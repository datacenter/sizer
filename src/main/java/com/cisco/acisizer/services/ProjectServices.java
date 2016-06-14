package com.cisco.acisizer.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.ProjectTable;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.domain.UserTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.AciFileIoException;
import com.cisco.acisizer.exceptions.AciSizingException;
import com.cisco.acisizer.exceptions.ProjectNameExistsException;
import com.cisco.acisizer.exceptions.ProjectTypeInvalidException;
import com.cisco.acisizer.helper.BdHelper;
import com.cisco.acisizer.helper.ProjectInventoryHelper;
import com.cisco.acisizer.models.Application;
import com.cisco.acisizer.models.Bd;
import com.cisco.acisizer.models.Contract;
import com.cisco.acisizer.models.Epg;
import com.cisco.acisizer.models.EpgPrefixes;
import com.cisco.acisizer.models.L3out;
import com.cisco.acisizer.models.Leaf;
import com.cisco.acisizer.models.LogicalRequirement;
import com.cisco.acisizer.models.LogicalSummary;
import com.cisco.acisizer.models.Options;
import com.cisco.acisizer.models.Project;
import com.cisco.acisizer.models.ProjectType;
import com.cisco.acisizer.models.SharedResource;
import com.cisco.acisizer.models.Subnets;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.models.UIData;
import com.cisco.acisizer.models.UserProfile;
import com.cisco.acisizer.models.Vrf;
import com.cisco.acisizer.physical.rest.models.View.Room;
import com.cisco.acisizer.physical.services.RoomServices;
import com.cisco.acisizer.physical.services.RowServices;
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.repo.RoomRepository;
import com.cisco.acisizer.repo.SwitchRepository;
import com.cisco.acisizer.repo.UsersRepository;
import com.cisco.acisizer.summary.SummaryElements;
import com.cisco.acisizer.summary.SummaryLeaf;
import com.cisco.acisizer.summary.SummaryResource;
import com.cisco.acisizer.summary.SummaryUIResource;
import com.cisco.acisizer.summary.SummaryUIResponse;
import com.cisco.acisizer.summary.SummaryUIUtilizationDetail;
import com.cisco.acisizer.ui.models.ApplicationConfiguration;
import com.cisco.acisizer.ui.models.ApplicationTemplate;
import com.cisco.acisizer.ui.models.ApplicationUi;
import com.cisco.acisizer.ui.models.LeafChoiceUi;
import com.cisco.acisizer.util.ACISizerConstant;
import com.cisco.acisizer.util.AutoNameGenerator;
import com.cisco.acisizer.util.FileUtilities;
import com.cisco.acisizer.util.SizingExecution;
import com.cisco.acisizer.util.Utility;
import com.google.gson.Gson;

@Service
public class ProjectServices {

	public static final String UNDERSCORE = "_";

	public static final String REGEX_TO_MATCH_MULTIPLE_WHITE_SPACES = " +";

	public static final String ACI_PROJECT_USERID_NAME_KEY_CONSTRAINT = "aci_project_userid_name_key";

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServices.class);

	public static final String DUPLICATE_ID_INCREMENTOR = "-";

	public static final String DUPLICATE_NAME_SEPERATOR = "-";

	public static final String NOT_A_VALID_PROJECT_TYPE = "Not a valid project type" ;

	@Inject
	private ProjectsRepository projectsRepository;

	@Inject 
	private UsersRepository usersRepository;
	
	@Inject
	private SizingExecution sizingExecution;

	@Inject
	private TenantServices tenantServices;

	@Inject
	private AppServices appServices;
	
	@Inject
	private EpgServices epgServices;

	@Inject
	private ContractServices contractServices;

	@Inject
	private AutoNameGenerator autoNameGenerator;
	
	@Inject
	private SwitchRepository switchRepository;
	
	@Inject
	private RoomServices roomServices;
	
	@Value("${cisco.remote.directory}")
	private String fileDirectory;

	@Inject
	private Gson gson;
	@Inject
	private ProjectInventoryHelper projectInventoryHelper;
	
	
	/**
	 * @param proj
	 * @param logicalRequirement
	 * @param logicalSummary
	 * @return
	 */
	public ProjectTable saveProject(ProjectTable proj, LogicalRequirement logicalRequirement,
			LogicalSummary logicalSummary) {
		proj.setLogicalRequirementSummary(gson.toJson(logicalSummary));
		ProjectTable returnProj = saveProject(proj, logicalRequirement);
		return returnProj;
	}

	/**
	 * @param proj
	 * @param logicalRequirement
	 * @return
	 */
	public ProjectTable saveProject(ProjectTable proj, LogicalRequirement logicalRequirement) {
		String logicalRequirementString = gson.toJson(logicalRequirement);
		proj.setLogicalRequirement(logicalRequirementString);
		proj.setLastUpdatedTime(new Timestamp(new Date().getTime()));
		ProjectTable returnProj = projectsRepository.save(proj);
		return returnProj;
	}
	
	
	private static final ThreadLocal<LoggingHelperModel> local=new ThreadLocal<LoggingHelperModel>(){
		@Override
		protected LoggingHelperModel initialValue() {
			return new LoggingHelperModel();
		}
	};

	public Project addProject(String userId, ProjectTable projTab) throws ProjectTypeInvalidException, ProjectNameExistsException {
		Date date = new Date();
		LogicalSummary logicalSummary = new LogicalSummary();
		LogicalRequirement logicalRequirement = new LogicalRequirement();
		logicalRequirement.setOptions(new Options());

		projTab.setUserId(userId);
		projTab.setCreatedTime(new Timestamp(date.getTime()));
		projTab.setLogicalRequirement(gson.toJson(logicalRequirement));
		projTab.setLastUpdatedTime(new Timestamp(date.getTime()));

		projTab.setLogicalRequirementSummary(gson.toJson(logicalSummary));
		projTab.setLogicalResultSummary("");
		projTab.setPhysicalRequirement("");
		projTab.setPhysicalRequirementSummary("");
		projTab.setPhysicalResultSummary("");
		// projTab.setDescription("");
		if (projTab.getType() == null) {
			projTab.setType(ProjectType.ACI.getValue());
		} else if (null == ProjectType.fromString(projTab.getType())) {
			throw new ProjectTypeInvalidException(NOT_A_VALID_PROJECT_TYPE);
		}

		/*if(projTab.getUserId()==null ||projTab.getUserId().isEmpty()){
			projTab.setUserId("1");
		}*/
		//projTab.setUserId(1);
		Tenant tenantCommon = createTenantCommon();
		Vrf vrfDefault = createVrfDefault();
		tenantCommon.getVrfs().add(vrfDefault);
		tenantCommon.getBds().add(createBdDefault());
		tenantCommon.getL3outs().add(createL3outDefault());
		tenantCommon.getSharedResources().add(createSharedResourceDefault());
		tenantServices.addTenant(tenantCommon, projTab);
		//Utility.setNodeCountForFabricPane(tenantCommon, logicalSummary);
		logicalSummary.setTenantCount(logicalSummary.getTenantCount() + 1);
		projTab.setLogicalRequirementSummary(gson.toJson(logicalSummary));
		ProjectTable projDb = null;
		ConstraintViolationException ex = null;
		try {
			projDb = projectsRepository.save(projTab);
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
				ex = (ConstraintViolationException) e.getCause();
			if (ex != null && ex.getSQLException() != null && ex.getSQLException().getNextException() != null
					&& ex.getSQLException().getNextException().getMessage() != null && ex.getSQLException()
							.getNextException().getMessage().contains(ACI_PROJECT_USERID_NAME_KEY_CONSTRAINT))
				throw new ProjectNameExistsException("project exists by name :" + projTab.getName());
		}

		saveUniqueIdCounter(projDb);

		Project proj = mapProjectDomainToProjectModel(projDb);

		logicalRequirement = gson.fromJson(projDb.getLogicalRequirement(), LogicalRequirement.class);
		logicalSummary = gson.fromJson(projDb.getLogicalRequirementSummary(), LogicalSummary.class);
		setEntityCountsInLogicalSummary(logicalRequirement.getTenants(), logicalSummary);
		proj.setLogicalRequirementSummary(logicalSummary);
		proj.setLogicalRequirement(logicalRequirement);

		// saveSnapshot(proj.getId(),tenantCommon);
		return proj;
	}

	private void saveUniqueIdCounter(ProjectTable projDb) {

		// this is a quick fix, however it is not a costly operation looking at
		// the frequency of creating project

		LogicalSummary logicalSummary = gson.fromJson(projDb.getLogicalRequirementSummary(), LogicalSummary.class);
		logicalSummary.generateUniqueId(projDb.getId());
		projDb.setLogicalRequirementSummary(gson.toJson(logicalSummary));
		projectsRepository.save(projDb);
	}

	private Vrf createVrfDefault() {
		Vrf vrf = new Vrf(ACISizerConstant.DEFAULT_VRF_ID, ACISizerConstant.DEFAULT_VRF_NAME);
		vrf.setType(ACISizerConstant.DEFAULT);
		vrf.setUiData(new UIData(20, 20));
		return vrf;
	}

	private Tenant createTenantCommon() {
		Tenant tenant = new Tenant(ACISizerConstant.DEFAULT_TENANT_ID, ACISizerConstant.TENANT_NAME_COMMON);
		tenant.setType(ACISizerConstant.TENANT_TYPE_UTILITY);
		return tenant;
	}

	private Bd createBdDefault() {
		Bd bd = new Bd(ACISizerConstant.DEFAULT_BD_ID, ACISizerConstant.BD_DEFAULT);
		bd.setVrf("" + ACISizerConstant.DEFAULT_VRF_ID);
		bd.setUiData(new UIData(20, 50));
		bd.setSubnets(new Subnets(1));
		bd.setOwnershipId(ACISizerConstant.DEFAULT_TENANT_ID);
		bd.setType(ACISizerConstant.DEFAULT);
		return bd;
	}

	private L3out createL3outDefault() {
		L3out l3out = new L3out(ACISizerConstant.DEFAULT_L3OUT_ID, ACISizerConstant.DEFAULT_L3OUT_NAME);
		l3out.setCount(1);
		l3out.setVrf("" + ACISizerConstant.DEFAULT_VRF_ID);
		l3out.setUiData(new UIData(20, 70));
		l3out.setEpg_prefixes(new EpgPrefixes(1));
		l3out.setSvis(1);
		l3out.setType(ACISizerConstant.DEFAULT);
		l3out.setOwnershipId(ACISizerConstant.DEFAULT_TENANT_ID);
		return l3out;
	}

	private SharedResource createSharedResourceDefault() {
		SharedResource obj = new SharedResource(ACISizerConstant.DEFAULT_SHARED_RESOURCE_ID,
				ACISizerConstant.DEFAULT_SHARED_RESOURCE_NAME);
		obj.setVrf("" + ACISizerConstant.DEFAULT_VRF_ID);
		obj.setUiData(new UIData(20, 90));
		return obj;
	}

	public Project updateProject(ProjectTable projTab, int id) throws AciEntityNotFound, ProjectNameExistsException {
		Date date = new Date();
		LogicalSummary logicalSummary = new LogicalSummary();
		ProjectTable projDb = projectsRepository.findOne(id);

		if (projDb == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID );
		}
		projDb.setAccount(projTab.getAccount());
		projDb.setCreatedBy(projTab.getCreatedBy());
		projDb.setCustomerName(projTab.getCustomerName());
		projDb.setName(projTab.getName());
		projDb.setOpportunity(projTab.getOpportunity());
		projDb.setSalesContact(projTab.getSalesContact());
		projDb.setType(projTab.getType());
		projDb.setDescription(projTab.getDescription());
		projDb.setLastUpdatedTime(new Timestamp(date.getTime()));
		
		ConstraintViolationException ex = null;
		try {
			projectsRepository.updateProject(projDb.getId(), projDb.getName(), projDb.getType(),
					projDb.getCustomerName(), projDb.getSalesContact(), projDb.getOpportunity(), projDb.getAccount(),
					projDb.getLastUpdatedTime(), projDb.getDescription());
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
				ex = (ConstraintViolationException) e.getCause();
			if (ex != null && ex.getSQLException() != null && ex.getSQLException().getNextException() != null
					&& ex.getSQLException().getNextException().getMessage() != null && ex.getSQLException()
							.getNextException().getMessage().contains(ACI_PROJECT_USERID_NAME_KEY_CONSTRAINT))
				throw new ProjectNameExistsException("project exists by name :" + projTab.getName());
		}
		
		Project proj = mapProjectDomainToProjectModel(projDb);

		proj.setLogicalRequirement(gson.fromJson(projDb.getLogicalRequirement(), LogicalRequirement.class));

		// convert LogicalRequirementSummary string to json object
		logicalSummary = gson.fromJson(projDb.getLogicalRequirementSummary(), LogicalSummary.class);
		proj.setLogicalRequirementSummary(logicalSummary);

		return proj;
	}

	/**
	 * @param projDb
	 * @return
	 */
	private Project mapProjectDomainToProjectModel(ProjectTable projDb) {
		Project proj = new Project();
		proj.setAccount(projDb.getAccount());
		proj.setCustomerName(projDb.getCustomerName());
		proj.setId(projDb.getId());
		proj.setSalesContact(projDb.getSalesContact());
		proj.setName(projDb.getName());
		proj.setOpportunity(projDb.getOpportunity());
		proj.setPhysicalRequirement(projDb.getPhysicalRequirement());
		proj.setPhysicalRequirementSummary(projDb.getPhysicalRequirementSummary());
		//proj.setPhysicalResultSummary(projDb.getPhysicalResultSummary());
		proj.setSalesContact(projDb.getSalesContact());
		proj.setType(ProjectType.fromString(projDb.getType()));
		//proj.setLogicalResultSummary(projDb.getLogicalResultSummary());
		proj.setCreatedBy(projDb.getCreatedBy());
		proj.setCreatedTime(projDb.getCreatedTime());
		proj.setLastUpdatedTime(projDb.getLastUpdatedTime());
		proj.setDescription(projDb.getDescription());
		proj.setRoomId(projDb.getRoomId());
		proj.setUsePhysical(projDb.isUsePhysical());
		return proj;
	}

	public int deleteProject(int id) throws AciEntityNotFound {

		if (projectsRepository.findOne(id) == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}
		projectsRepository.delete(id);

		if (projectsRepository.findOne(id) == null)
			return 1;
		else
			return 0;
	}

	public Project getProject(int id) throws AciEntityNotFound, AciFileIoException, AciSizingException {
		return getProjectFromDB(id);
	}
	
	public Project getSizingREsults(int id) throws AciEntityNotFound, AciFileIoException, AciSizingException{
		calculateAndSaveSizingSummary(id);
		return getProjectFromDB(id);
	}

	/**
	 * @param id
	 * @return
	 * @throws AciEntityNotFound
	 */
	public Project getProjectFromDB(int id) throws AciEntityNotFound {
		ProjectTable projDb = projectsRepository.findOne(id);
		if (projDb == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		} else {

			Project proj = mapProjectDomainToProjectModel(projDb);

			proj.setLogicalRequirement(gson.fromJson(projDb.getLogicalRequirement(), LogicalRequirement.class));

			// convert LogicalRequirementSummary string to json object
			LogicalSummary logicalSummary = gson.fromJson(projDb.getLogicalRequirementSummary(), LogicalSummary.class);
			logicalSummary.setTenantCount(proj.getLogicalRequirement().getTenants().size());
			setEntityCountsInLogicalSummary(proj.getLogicalRequirement().getTenants(), logicalSummary);
			proj.setLogicalRequirementSummary(logicalSummary);
			
			return proj;
		}
	}

	public List<Project> getProjects(String userId) {
		
		generateTemplateProjects(userId);
		
		List<ProjectTable> projsTable = projectsRepository.findProjectByUserId(userId);
		List<Project> projList = new ArrayList<Project>();
		mapProjects(projsTable, projList);

		Collections.sort(projList);
		return projList;

	}

	private void mapProjects(List<ProjectTable> srcProjsTable,
			List<Project> destProjList) {
		LogicalSummary logicalSummary = new LogicalSummary();

		for (int i = 0; i < srcProjsTable.size(); i++) {
			ProjectTable projDb = srcProjsTable.get(i);

			Project proj = mapProjectDomainToProjectModel(projDb);

			proj.setLogicalRequirement(gson.fromJson(projDb.getLogicalRequirement(), LogicalRequirement.class));

			// convert LogicalRequirementSummary string to json object
			logicalSummary = gson.fromJson(projDb.getLogicalRequirementSummary(), LogicalSummary.class);
			setEntityCountsInLogicalSummary(proj.getLogicalRequirement().getTenants(),logicalSummary);
			proj.setLogicalRequirementSummary(logicalSummary);

			destProjList.add(proj);
		}
	}

	private void generateTemplateProjects(String userId) {
		
		try
		{
			boolean firstTimeLogin = false;
			if(null == usersRepository.getUser(userId))
			{
				firstTimeLogin = true;
				UserTable newUser = new UserTable();
				newUser.setUserId(userId);
	
				UserProfile profile = new UserProfile();
				profile.setFirstLogin(false);
				String strProfile = gson.toJson(profile);
				newUser.setUserProfile(strProfile);
				
				usersRepository.save(newUser);
			}
			
			if(firstTimeLogin)
			{
				createTemplateProject("CommonTenantExample","Fabric elements in Common Tenant",userId,false);
				createTemplateProject("LocalTenantExample","Fabric elements within user Tenant",userId,true);
			}
		}
		catch(Exception ex)
		{
			LOGGER.info("Exception generating the template projects for "+userId+". "+ex.getMessage());
		}
	}

	private void createTemplateProject(String projName, String projDescription, String userId,boolean appTemplateUnique) {
		// TODO Auto-generated method stub
		try
		{
			ProjectTable proj = new ProjectTable();
			proj.setName(projName);
			proj.setDescription(projDescription);
			proj.setAccount("Cisco");
			Project projRet = addProject(userId,proj);
			Tenant tenantRet = null;
			Tenant inputTenant = new Tenant(50,"tenant-1");
			inputTenant.setCount(1);
			if(appTemplateUnique)
			{
				inputTenant.setLocalVrf(true);
				inputTenant.setLocalL3out(true);
				inputTenant.setL3outComplexity(ACISizerConstant.TEMPLATE_L3OUT_COMP_MEDIUM);
				tenantRet = tenantServices.addTenantTemplate(projRet.getId(), inputTenant);
			}
			else
				tenantRet = tenantServices.addTenant(projRet.getId(), inputTenant);
			
			ApplicationTemplate appTemplate = new ApplicationTemplate();
			appTemplate.setName("app-1");
			appTemplate.setModel(ACISizerConstant.TEMPLATE_APP_MODEL_3TIER);
			appTemplate.setNoOfInstances(1);
			
			if(null == appTemplate.getConfiguration())
				appTemplate.setConfiguration(new ApplicationConfiguration());

			appTemplate.getConfiguration().setContractComplexity(ACISizerConstant.TEMPLATE_CONTRACT_COMP_MEDIUM);
			appTemplate.getConfiguration().setEpgComplexity(ACISizerConstant.TEMPLATE_EPG_COMP_MEDIUM);
			appTemplate.getConfiguration().setL3outComplexity(ACISizerConstant.TEMPLATE_L3OUT_COMP_MEDIUM);
			appTemplate.getConfiguration().setL3outEnabled(true);
			appTemplate.getConfiguration().setSharedServiceEnabled(true);
			if(true == appTemplateUnique)
			{
				appTemplate.getConfiguration().setBdPolicy(ACISizerConstant.TEMPLATE_CONF_UNIQUE);
				appTemplate.getConfiguration().setSubnetPolicy(ACISizerConstant.TEMPLATE_CONF_UNIQUE);
			}
			else
			{
				appTemplate.getConfiguration().setBdPolicy(ACISizerConstant.TEMPLATE_CONF_DEFAULT);
				appTemplate.getConfiguration().setSubnetPolicy(ACISizerConstant.TEMPLATE_CONF_DEFAULT);
			}
			
			ApplicationUi appRet = appServices.addAppTemplate(projRet.getId(), tenantRet.getId(), appTemplate);
			
			
		}
		catch(Exception ex)
		{
			LOGGER.info("Failed creating template projects for " + userId + ". "+ex.getMessage());
		}
	}

	private void setEntityCountsInLogicalSummary(List<Tenant> tenants, LogicalSummary logicalSummary) {
		int vrfCount = 0;
		int bdCount = 0;
		int epgCount=0;
		int filters=0;
		int contract=0;
		int tenantCount=0;
		int endPoints=0;
		for (Tenant tenant : tenants) {
			vrfCount = vrfCount + tenant.getVrfs().size()*tenant.getCount();
			int bdCountTemp=0;
			for (Bd bd : tenant.getBds()) {
				bdCountTemp = bdCountTemp + bd.getNoOfInstances();
				
			}
			bdCount=bdCount+bdCountTemp*tenant.getCount();
			tenantServices.setEntityCounts(tenant);
			epgCount=epgCount+tenant.getTotalEpgCount()*tenant.getCount();
			endPoints=endPoints+tenant.getTotalEndPoints()*tenant.getCount();
			filters=filters+tenant.getTotalFilterCount()*tenant.getCount();
			contract=contract+tenant.getTotalContractCount()*tenant.getCount();
			tenantCount=tenantCount+tenant.getCount();
		}
		logicalSummary.setBdCount(bdCount);
		logicalSummary.setVrfCount(vrfCount);
		logicalSummary.setEpgCount(epgCount);
		logicalSummary.setFilterCount(filters);
		logicalSummary.setContractCount(contract);
		logicalSummary.setTenantCount(tenantCount);
		logicalSummary.setEndPoints(endPoints);
	}

	public String getSizingSummaryInput(int id) throws AciEntityNotFound {
		ProjectTable proj = projectsRepository.findOne(id);
		if (proj == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}
		LogicalRequirement logicalRequirement = gson.fromJson(proj.getLogicalRequirement(), LogicalRequirement.class);
		LogicalSummary logicalSummary = gson.fromJson(proj.getLogicalRequirementSummary(), LogicalSummary.class);
		String logicalRequirementInput = prepareInputToTool(logicalRequirement,logicalSummary, proj);
		return logicalRequirementInput.replace("\'", "\"");
	}

	public SummaryUIResponse getSizingSummary(int id) throws AciEntityNotFound, AciFileIoException, AciSizingException {
		ProjectTable proj = projectsRepository.findOne(id);
		if (proj == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}
		return getSummary(proj.getLogicalResultSummary());
	}

	/**
	 * @param id
	 * @return
	 * @throws AciEntityNotFound
	 * @throws AciFileIoException
	 * @throws AciSizingException
	 */
	private String calculateAndSaveSizingSummary(int id)
			throws AciEntityNotFound, AciFileIoException, AciSizingException {
		ProjectTable proj = projectsRepository.findOne(id);
		if (proj == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID + id);
		}
		LogicalRequirement logicalRequirement = gson.fromJson(proj.getLogicalRequirement(), LogicalRequirement.class);
		LogicalSummary logicalSummary = gson.fromJson(proj.getLogicalRequirementSummary(), LogicalSummary.class);
		
		LOGGER.info("generating input to the tool " + proj.getName());
		local.get().setInputGenerationStartTime(System.currentTimeMillis());
		String logicalRequirementInput = prepareInputToTool(logicalRequirement,logicalSummary, proj);

		logicalRequirementInput = logicalRequirementInput.replace("\'", "\"");
		LOGGER.info("input to the tool generated  " + proj.getName());
		local.get().setInputGenerationStopTime(System.currentTimeMillis());
		String result = "";

		LOGGER.info("writing input json file " +  proj.getName());
		String fileName = FileUtilities.generateUniqueFileName(
				proj.getName().trim().replaceAll(REGEX_TO_MATCH_MULTIPLE_WHITE_SPACES, UNDERSCORE));
		sizingExecution.writeJsonToFile(fileDirectory, logicalRequirementInput, fileName);

		LOGGER.info("writing input json file completed "+ proj.getName());
		LOGGER.info("sizing execution started "+ proj.getName());
		local.get().setExecutionStartTime(System.currentTimeMillis());
		
		result = sizingExecution.executeTool(fileDirectory, fileName);
		
		LOGGER.info("sizing execution completed "+ proj.getName());
		local.get().setExecutionStopTime(System.currentTimeMillis());
		
		sizingExecution.deleteTheGeneratedFile(fileDirectory, fileName);
		
		LOGGER.info("sizing result processing started "+ proj.getName());
		local.get().setResultProcessingStartTime(System.currentTimeMillis());
		SummaryElements elements = null;
		try {
			elements = gson.fromJson(result, SummaryElements.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Results  :" + result);
			throw e;
		}
		logicalSummary.setLeafCountBorder(0);
		logicalSummary.setLeafCountRegular(0);
		if (elements != null && elements.getUtilization() != null) {
			for (SummaryLeaf leaf : elements.getUtilization().getLeafs()) {
				if (leaf.getLabels().contains("Border Leaf")) {
					logicalSummary.setLeafCountBorder(logicalSummary.getLeafCountBorder() + 1);
				} else {
					logicalSummary.setLeafCountRegular(logicalSummary.getLeafCountRegular() + 1);
				}
			}
		}
		proj.setLogicalRequirementSummary(gson.toJson(logicalSummary));
		proj.setLogicalResultSummary(result);
		projectsRepository.save(proj);

		LOGGER.info("sizing results processing completed "+ proj.getName());
		local.get().setResultProcessingStopTime(System.currentTimeMillis());
		LOGGER.info("projectTimeStatistics : "+local.get().toString());
		
		return result;
	}

	private String prepareInputToTool(LogicalRequirement logicalRequirement, LogicalSummary logicalSummary, ProjectTable proj) {
		Tenant commonTenant = Utility.getCommonTenant(logicalRequirement);
		for (Tenant tenant : logicalRequirement.getTenants()) {
			duplicateBds(tenant);
			duplicateApplicationEntities(tenant, commonTenant);
		}
		setCountOfApplicationentitiesLog(logicalRequirement);
		int requiredBorderLeaf=calculateNumOfBorderLeafs(logicalRequirement);
		HashMap<String, Integer> switches=calulateNumOfSwitches(logicalRequirement, proj);
		generateLeafNodes(logicalRequirement,switches,requiredBorderLeaf);
		return gson.toJson(logicalRequirement);
	}
	
	private void generateLeafNodes(LogicalRequirement logicalRequirement, HashMap<String, Integer> switches,
			int requiredBorderLeaf) {
		int nameOffset=1;
		String leafName="Leaf";
		for (String model : switches.keySet()) {
			Integer currSwitchCount = switches.get(model);
			if(currSwitchCount>requiredBorderLeaf){
				Leaf leaf=new Leaf();
				leaf.setCount(requiredBorderLeaf);
				List<String> label=new ArrayList<String>();
				label.add("Border Leaf");
				leaf.setLabels(label);
				leaf.setModel(model);
				leaf.setName( leafName+ nameOffset);
				nameOffset= nameOffset+1;
				logicalRequirement.getLeafs().add(leaf);
				switches.put(model, currSwitchCount-requiredBorderLeaf);
				
				break;
			}else{
				Leaf leaf=new Leaf();
				leaf.setCount(currSwitchCount);
				List<String> label=new ArrayList<String>();
				label.add("Border Leaf");
				leaf.setLabels(label);
				leaf.setModel(model);
				leaf.setName( leafName+ nameOffset);
				nameOffset= nameOffset+1;
				logicalRequirement.getLeafs().add(leaf);
				switches.put(model, 1);
				requiredBorderLeaf=requiredBorderLeaf-currSwitchCount;
			}
		}
		
		for (String model : switches.keySet()) {
			Integer currSwitchCount = switches.get(model);
			Leaf leaf=new Leaf();
			leaf.setCount(currSwitchCount);
			List<String> label=new ArrayList<String>();
			label.add("Regular Leaf");
			leaf.setLabels(label);
			leaf.setModel(model);
			leaf.setName( leafName+ nameOffset);
			nameOffset= nameOffset+1;
			logicalRequirement.getLeafs().add(leaf);
		}
	}

	private HashMap<String, Integer> calulateNumOfSwitches(LogicalRequirement logicalRequirement, ProjectTable proj) {
		HashMap<String, Integer> aggregateSwitches = new HashMap<String, Integer>();
		for (SwitchTable switchIter : switchRepository.getAllTorsOfRoom(proj.getRoomId())) {
			String name=switchIter.getDeviceType().getName();
			if (aggregateSwitches.containsKey(name)) {
				 int cuurValue=aggregateSwitches.get(name);
				 aggregateSwitches.put(name, cuurValue+1);
			}else{
				aggregateSwitches.put(name,1);
			}
		}
		return aggregateSwitches;
	}

	private int calculateNumOfBorderLeafs(LogicalRequirement logicalRequirement) {
	    // Number of border leaves required is the number of instances of L3outs to be placed.
	    // Note we can't use VRFs, multiple VRFs can be hosted on same leaf.
	    // Also take into account span count.
	    // Multiple L3outs can be placed on same leaf. So need not worry about multiple instances of L3out.
	    //

	    int maxSpan=0;

		for (Tenant tenant : logicalRequirement.getTenants()) {
			for (L3out l3out : tenant.getL3outs()) {
			    if (l3out.getSpan() > maxSpan)
			        maxSpan = l3out.getSpan();
			}
		}
		if (maxSpan <=1)
		        maxSpan=2;


		return maxSpan;
	}

	private void setCountOfApplicationentitiesLog(LogicalRequirement logicalRequirement) {
		int contractCount = 0;
		int epgCount = 0;
		int tenantCount=0;
		for (Tenant tenant : logicalRequirement.getTenants()) {
			contractCount=contractCount+tenant.getContracts().size();
			epgCount=epgCount+tenant.getEpgs().size();
			tenantCount=tenantCount+tenant.getCount();
		}
		local.get().setTenantCount(tenantCount);
		local.get().setContractCount(contractCount);
		local.get().setEpgCount(epgCount);
	}

	private void duplicateBds(Tenant tenant) {
		Set<Bd> bdDuplicates=new LinkedHashSet<>();
		for (Bd bd : tenant.getBds()) {
			for(int i=0;i<bd.getNoOfInstances()-1;i++){
				int j= i+2;
				Bd bdTemp=new Bd(bd.getId()+DUPLICATE_ID_INCREMENTOR+j, bd.getDisplayName()+DUPLICATE_NAME_SEPERATOR+j);
				bdTemp.copyBd(bd);
				bdDuplicates.add(bdTemp);
			}
			
		}
		tenant.getBds().addAll(bdDuplicates);
	}

	/**
	 * Method will duplicate the entities(Epgs and contracts) based on the
	 * number of instance under application this will loop through the
	 * application instances and call duplicateContracts and duplicateEpgs
	 * 
	 * @param tenant
	 * @param commonTenant
	 * @param logicalSummary 
	 */
	private void duplicateApplicationEntities(Tenant tenant, Tenant commonTenant) {
		for (Application application : tenant.getApps()) {
			if (application.getInstances() > 1) {
				List<Epg> epgs = getEpgsOfApplication(tenant, application);
				List<Contract> contracts = getContractsOfApplication(tenant, application);
				for (int i = 0; i < application.getInstances() - 1; i++) {
					if (!epgs.isEmpty()) {
						duplicateEpgs(tenant, epgs, i+2);
					}
					if (!contracts.isEmpty()) {
						duplicateContracts(tenant, commonTenant, contracts, i+2);
					}
				}
			}
			
			//no contracts were created while shared service were enabled, creating the contracts now
			//by this time all the instances of epgs are created, we are ready to create the contract
			List<Epg> epgsForSharedResource = getEpgsOfApplicationForSharedResource(tenant, application);
			generateSharedResourceContract(tenant,commonTenant,epgsForSharedResource);
			
		}

	}

	private List<Epg> getEpgsOfApplicationForSharedResource(Tenant tenant,
			Application application) {
		List<Epg> epgs = new ArrayList<>();
		if (tenant.getEpgs() != null && !tenant.getEpgs().isEmpty()) {
			for (Epg epg : tenant.getEpgs()) {
				if (epg.getApp().equals(application.getName()) && epg.isEnableSharedReource()) {
					epgs.add(epg);
				}
			}
		}
		return epgs;
	}

	private void generateSharedResourceContract(Tenant tenant, Tenant commonTenant, List<Epg> epgs) {
		int i = 2;
		for (Epg epg : epgs) {
			if (epg.isEnableSharedReource()) {
				Contract contractToAdd = new Contract(epg.getName() + DUPLICATE_ID_INCREMENTOR + i,
						epg.getDisplayName() + DUPLICATE_NAME_SEPERATOR, epg.getApp());

				contractToAdd.setProviderId(epg.getName());
				contractToAdd.setProviderType(ACISizerConstant._epg);
				contractToAdd.setConsumerId("" + epg.getSharedResourceId());
				contractToAdd.setConsumerType(ACISizerConstant._shared_resource);
				contractServices.addContract(contractToAdd, commonTenant, tenant);
				i = i + 1;
			}

		}
	}

	/**
	 * Method to duplicate the contracts of the application based on the
	 * instance parameter of the app This will create a copy of all the
	 * contracts under the given application
	 * 
	 * @param tenant
	 * @param commonTenant
	 * @param contracts
	 * @param index
	 */
	private void duplicateContracts(Tenant tenant, Tenant commonTenant, List<Contract> contracts, int index) {

		for (Contract contract : contracts) {
			Contract contractToAdd = new Contract(contract.getId() + DUPLICATE_ID_INCREMENTOR + index,
					contract.getDisplayName() + DUPLICATE_NAME_SEPERATOR + index, contract.getAppName());
			contractToAdd.copyContract(contract);
			// contractToAdd.setId(contract.getId() + DUPLICATE_ID_INCREMENTOR +
			// index);
			if (contract.getProviderType().equals(ACISizerConstant._epg)) {
				contractToAdd.setProviderId(contract.getProviderId() + DUPLICATE_ID_INCREMENTOR + index);
			}
			if (contract.getConsumerType().equals(ACISizerConstant._epg)) {
				contractToAdd.setConsumerId(contract.getConsumerId() + DUPLICATE_ID_INCREMENTOR + index);
			}
			contractServices.addContract(contractToAdd, commonTenant, tenant);

		}
	}

	/**
	 * Method will return all the contracts under given application
	 * 
	 * @param tenant
	 * @param application
	 * @return list<contracts> under the given application
	 */
	private List<Contract> getContractsOfApplication(Tenant tenant, Application application) {
		List<Contract> contracts = new ArrayList<>();
		if (tenant.getContracts() != null && !tenant.getContracts().isEmpty()) {
			for (Contract contract : tenant.getContracts()) {
				if (contract.getAppName().equals(application.getName())) {
					contracts.add(contract);
				}
			}
		}
		return contracts;
	}

	/**
	 * Method to duplicate the epgs of the application based on the instance
	 * parameter of the app This will create a copy of all the epgs under the
	 * given application
	 * 
	 * @param tenant
	 * @param epgs
	 * @param epgs
	 * @param index
	 */
	private void duplicateEpgs(Tenant tenant, List<Epg> epgs, int index) {

		for (Epg epg : epgs) {
			Epg epgToAdd = new Epg("" + epg.getId() + DUPLICATE_ID_INCREMENTOR + index,
					epg.getDisplayName() + DUPLICATE_NAME_SEPERATOR + index, epg.getApp());
			epgToAdd.copyEpg(epg);
			epgToAdd.setConsumed_contracts(null);
			epgToAdd.setProvided_contracts(null);
			Bd bd = BdHelper.getBdbyName(epg.getBd(), tenant);
			if (bd!=null && bd.getNoOfInstances() > 1) {
				epgToAdd.setBd(epg.getBd() + DUPLICATE_ID_INCREMENTOR + index);
			}
			// epgToAdd.setId(epg.getId() + 1000 + index);
			epgServices.addEpg(epgToAdd, tenant);
		}

	}

	/**
	 * Method will return all the epgs under given application
	 * 
	 * @param tenant
	 * @param application
	 * @return list<Epg> under the given application
	 */
	private List<Epg> getEpgsOfApplication(Tenant tenant, Application application) {
		List<Epg> epgs = new ArrayList<>();
		if (tenant.getEpgs() != null && !tenant.getEpgs().isEmpty()) {
			for (Epg epg : tenant.getEpgs()) {
				if (epg.getApp().equals(application.getName())) {
					epgs.add(epg);
				}
			}
		}
		return epgs;
	}

	private SummaryUIResponse getSummary(String result) {
		SummaryUIResponse response = new SummaryUIResponse();
		SummaryElements elements = gson.fromJson(result, SummaryElements.class);
		

		if (elements!=null && null != elements.getUtilization()) {
			Random rnd = new Random();

			for (SummaryLeaf leaf : elements.getUtilization().getLeafs()) {
				int randomId = rnd.nextInt(ACISizerConstant.RANDOM_NUMBER_GENERATION_MAX);
				SummaryUIResource data = new SummaryUIResource();
				data.setSwitch(leaf.getName());
				data.setId(randomId);
				for (SummaryResource rc : leaf.getResources()) {
					fillResourceName(rc, data);
				}
				response.getResourceGridData().add(data);
			}
		}
		return response;
	}

	private void fillResourceName(SummaryResource rc, SummaryUIResource data) {

		switch (rc.getName()) {

		case ACISizerConstant.SUMMARY_UI_Vrf:
			data.setVRFs(rc.getCount());
			break;

		case ACISizerConstant.SUMMARY_UI_Bd:
			data.setBDs(rc.getCount());
			break;

		// case ACISizerConstant.SUMMARY_UI_Endpoint:
		case ACISizerConstant.SUMMARY_UI_Epg:
			data.setEPGs(rc.getCount());
			data.setEndpoints(rc.getCount());
			break;

		case ACISizerConstant.SUMMARY_UI_Contract:
			data.setContracts(rc.getCount());
			break;

		case ACISizerConstant.SUMMARY_UI_L3out:
			data.setL3Out(rc.getCount());
			break;

		case ACISizerConstant.SUMMARY_UI_Source_Prefix_TCAM:
		case ACISizerConstant.SUMMARY_UI_Dest_Prefix_TCAM:
		case ACISizerConstant.SUMMARY_UI_Policy_TCAM:
		case ACISizerConstant.SUMMARY_UI_Router_IP_Table:
		case ACISizerConstant.SUMMARY_UI_VLAN_Table: {
			SummaryUIUtilizationDetail detail = new SummaryUIUtilizationDetail();

			String name = String.format("%s(%d)", rc.getName(), rc.getCount());
			detail.setName(name);
			detail.setValue(rc.getPct_usage());
			detail.setDisplayCount(String.format("%d (%d%s)", rc.getCount(),rc.getPct_usage(),"%"));
			data.getUtilizationDetails().add(detail);
		}
			break;

		}
	}

	public Tenant getDefaultValueTenant(int projId) throws AciEntityNotFound {
		ProjectTable proj = projectsRepository.findOne(projId);

		if (proj == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}

		// convert it to java obj
		LogicalRequirement logicalRequirement = gson.fromJson(proj.getLogicalRequirement(), LogicalRequirement.class);
		List<String> tenantNames = getTenantDisplayName(logicalRequirement);

		Tenant tenant = new Tenant(10, autoNameGenerator.generateName(tenantNames, ACISizerConstant._tenant));
		tenant.setType(ACISizerConstant.TENANT_TYPE_USER);
		return tenant;
	}

	private List<String> getTenantDisplayName(
			LogicalRequirement logicalRequirement) {
/*		
		List<String> tenantNames = logicalRequirement.getTenants().parallelStream()
				.map(tenant -> tenant.getDisplayName()).collect(Collectors.toList());
*/
		List<String> tenantNames = new ArrayList<String>();
		for(Tenant iter : logicalRequirement.getTenants())
		{
			tenantNames.add(iter.getDisplayName());
		}
		
		return tenantNames;
	}

	/**
	 * Method for createClone(int projectId,)
	 * @param userId 
	 */
	@Transactional
	public Project createClone(String userId, int projectId) {
		Date date = new Date();
		ProjectTable projDb = projectsRepository.findOne(projectId);
		List<String> names = projectsRepository.getProjectNames();
		String name = projDb.getName() + ACISizerConstant.COPY;
		name = getAutoGeneratedName(names, name);
		ProjectTable target = new ProjectTable();
		target.copyProjectTable(projDb);
		target.setRoomId(projDb.getRoomId());
		target.setUsePhysical(projDb.isUsePhysical());
		target.setName(name);
		target.setUserId(userId);

		target.setLastUpdatedTime(new Timestamp(date.getTime()));
		target.setCreatedTime(new Timestamp(date.getTime()));
		ProjectTable projFinal = projectsRepository.save(target);

		// target.setRooms(new ArrayList<RoomTable>());
		for (RoomTable room : projDb.getRooms()) {
			roomServices.projectRoomClone(projFinal, room);
		}

		Project proj = mapProjectDomainToProjectModel(projFinal);
		return proj;
	}

	/**
	 * method for checking duplicate name in database and getting auto generated
	 * name
	 * 
	 * @param names
	 * @param name
	 * @return name
	 */

	private String getAutoGeneratedName(List<String> names, String name) {
		boolean flag = true;
		while (flag) {
			if (names.contains(name)) {
				name = name + ACISizerConstant.COPY;
			} else {
				return name;
			}

		}
		return name;
	}

	public List<Project> searchProjects(String userId, String query,
			int pageNumber, int pageSize) {

		Pageable pageRequest = new PageRequest(pageNumber, pageSize);
		List<ProjectTable> projsTable = projectsRepository.searchProjects(userId,query, pageRequest);
		List<Project> projList = new ArrayList<Project>();
		mapProjects(projsTable, projList);
		return projList;
	}

	public InventoryInfo getAllRoomsInventory(int projectId) throws AciEntityNotFound {
		ProjectTable projectTable = projectsRepository.findOne(projectId);
		if (projectTable == null) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}
		return projectInventoryHelper.calculateAllRoomsInventoryInfo(projectId, projectsRepository);
	}
	
	
	public void postPreferredSwitch(int projectId, LeafChoiceUi leafChoiceUi) throws AciEntityNotFound {

		ProjectTable projectTable = projectsRepository.findOne(projectId);
		if (null == projectTable) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}

		if (true == leafChoiceUi.isUsePhysical()) {
			projectTable.setUsePhysical(leafChoiceUi.isUsePhysical());
			projectTable.setRoomId(leafChoiceUi.getRoomId());
		}else{
			projectTable.setUsePhysical(leafChoiceUi.isUsePhysical());
			projectTable.setRoomId(0);
		}
		projectsRepository.save(projectTable);
	}
	
	
}
	
/*	public void callSizingSummary(final int projectId) {
		Thread t1 = new Thread(new Runnable() {

			
			@Override
			public void run() {
				try {
					calculateAndSaveSizingSummary(projectId);
				} catch (AciEntityNotFound | AciFileIoException | AciSizingException e) {
					LOGGER.debug("Exception in sizing" + e);
				}
			}
		});
		t1.start();
	}*/


package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cisco.acisizer.CiscoaciApplication;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.models.Project;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.models.UIData;
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.ui.models.ApplicationUi;
import com.cisco.acisizer.ui.models.BdUi;
import com.cisco.acisizer.ui.models.ContractUi;
import com.cisco.acisizer.ui.models.EpgUi;
import com.cisco.acisizer.ui.models.L3outUi;
import com.cisco.acisizer.ui.models.VrfUi;
import com.cisco.acisizer.util.ACISizerConstant;
import com.cisco.acisizer.utils.ShadowModel;
import com.cisco.acisizer.utils.TestingConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component
public class EpgControllerTest extends ControllerTest {
	
	public static String EPG_NAME = "epg5";
	public static String UPDATED_EPG_NAME = "epg10";
	public static String NEW_UPDATED_EPG_NAME = "epg11";
	public static String ADD_EPG_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + EPG_NAME + "\"}";
	public static String UPDATE_EPG_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + UPDATED_EPG_NAME + "\"}";
	
	
	
	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	private ProjectControllerTest projectControllerTest;
	
	@Inject
	private TenantControllerTest tenantControllerTest;
	
	@Inject
	private VrfControllerTest vrfControllerTest;
	
	@Inject
	private AppControllerTest appControllerTest;
	
	@Inject
	private BdControllerTest bdControllerTest;

	@Inject
	private Gson gson;

	private Project project;
	
	private Tenant tenant;
	
	private ApplicationUi app;
	
	private VrfUi vrf;
	
	

	@Before
	public void setUp() {
		RestAssured.port = 8090;
		projectsRepository.deleteAll();
		
		project = projectControllerTest.addDummyProject();
		tenant = tenantControllerTest.addDummyTenant(project.getId(), TenantControllerTest.ADD_TENANT_INPUT);
		vrf = vrfControllerTest.addDummyVrfByName(project.getId(),tenant.getId(),TestingConstants.DUMMY);
		app = appControllerTest.addDummyApp(project.getId(),tenant.getId(), AppControllerTest.ADD_APP_INPUT);

		
	}
	
	
	public EpgUi addDummyEpg(int projId, int tenantId, int appId, String name) {
		Response response = given().body(name).contentType(ContentType.JSON).when().post(TestingConstants.ADD_EPG_URI,
				projId, tenantId, appId );
		response.then().statusCode(HttpStatus.SC_OK);

		return gson.fromJson(response.asString(), EpgUi.class);
	}

	public EpgUi addDummyEpgByName(int projId, int tenantId,int appId, String name) {
		EpgUi dummyEpg = new EpgUi();
		dummyEpg.setName(name);
		dummyEpg.setBdId(ACISizerConstant.DEFAULT_BD_ID);
		return addDummyEpg(projId, tenantId, appId, gson.toJson(dummyEpg));
	}

	
	 /**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#testForDuplicateEpg(int, int, String)}
	 * .
	 */
	@Test
	public void testForDuplicateEpg() {
		addDummyEpgByName(project.getId(), tenant.getId(), app.getId(), "dummy");
		EpgUi dummyEpg = new EpgUi();
		dummyEpg.setName("dummy");
		dummyEpg.setBdId(ACISizerConstant.DEFAULT_BD_ID);

		Response response = given().body(gson.toJson(dummyEpg)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_EPG_URI, project.getId(), tenant.getId(), app.getId());
		response.then().statusCode(HttpStatus.SC_CONFLICT);
	}
	
	/**
	 * Test case for delete EPG.
	 * Scenarios : to delete the EPG that has contracts.
	 */
	@Test
	public void testToDeleteEpgWithContracts() {
		createNodes();
		Response respObject = null;
		int projectId = createProject("proj19");
		int tenantId = createTenant("tenant1", projectId);
		int vrfId = createVrf("vrf1", projectId, tenantId);
		int bdId = createBd("bd2", projectId, tenantId, vrfId);

		String appName = "DeleteEpg";
		int appId = createApplication(appName, projectId, tenantId);

		int epgId1 = createEpg("epg1", appName, bdId, projectId, tenantId, appId);
		int epgId2 = createEpg("epg2", appName, bdId, projectId, tenantId, appId);

		ContractUi ct = new ContractUi();
		ct.setName("ContractToEpg1ToEpg2");
		ct.setProviderId(epgId1);
		ct.setProviderType(ACISizerConstant._epg);

		ct.setConsumerId(epgId2);
		ct.setConsumerType(ACISizerConstant._epg);

		respObject = postRequest(ct, TestingConstants.PROJECT_BASE_URI + "/" + projectId + "/tenant/" + tenantId
				+ "/app/" + appId + "/contract");
		respObject.then().statusCode(HttpStatus.SC_OK);

		ShadowModel objContract = gson.fromJson(respObject.asString(), ShadowModel.class);
		int contractId = objContract.getId();

		respObject = when().delete(TestingConstants.PROJECT_BASE_URI + "/" + projectId + "/tenant/" + tenantId + "/app/"
				+ appId + "/contract/" + contractId);
		respObject.then().statusCode(HttpStatus.SC_OK);

		respObject = when().delete(TestingConstants.PROJECT_BASE_URI + "/" + projectId + "/tenant/" + tenantId + "/app/"
				+ appId + "/epg/" + epgId1);
		respObject.then().statusCode(HttpStatus.SC_OK);

	}
	public void addEpgWithSharedResource() {
		String epgName = "epg2";

		com.cisco.acisizer.ui.models.EpgUi epg = new com.cisco.acisizer.ui.models.EpgUi();
		epg.setBdId(m_bdId);
		epg.setSharedServicesEnabled(true);
		epg.setSpan(1);
		epg.setName(epgName);
		epg.setAppId(m_appId);
		epg.setSharedServiceId(m_sharedResourceId);
		epg.setNoOfsharedServiceFilter(4);
		Response respObject = postRequest(epg, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/epg");
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name", Matchers.is(epgName));
		respObject.then().body("appId", Matchers.is(m_appId));
		respObject.then().body("bdId", Matchers.is(m_bdId));
		respObject.then().body("id", Matchers.greaterThan(m_minId));

		String resp = when().get(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId
				+ "/app/" + m_appId + "/contracts").body().asString();
		Type type = new TypeToken<List<com.cisco.acisizer.ui.models.ContractUi>>() {
		}.getType();
		List<com.cisco.acisizer.ui.models.ContractUi> contracts = gson.fromJson(resp, type);

		int filterCount = 0;
		int contractConsumers = 0;
		for (com.cisco.acisizer.ui.models.ContractUi iter : contracts) {
			if (m_sharedResourceId == iter.getConsumerId()) {
				contractConsumers++;
				filterCount += iter.getNoOfFilters();
			}
		}

		assertEquals(contractConsumers, 1);
		assertEquals(filterCount, 4);
	}

	@Test
	public void deleteEpgWithSharedResource() {
		createNodes();

		String epgName = "epg3";
		com.cisco.acisizer.ui.models.EpgUi epg = new com.cisco.acisizer.ui.models.EpgUi();
		epg.setBdId(m_bdId);
		epg.setSharedServicesEnabled(true);
		epg.setSpan(1);
		epg.setName(epgName);
		epg.setAppId(m_appId);
		epg.setSharedServiceId(m_sharedResourceId);
		epg.setNoOfsharedServiceFilter(1);

		Response respObject = postRequest(epg, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/epg");
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name", Matchers.is(epgName));
		respObject.then().body("appId", Matchers.is(m_appId));
		respObject.then().body("bdId", Matchers.is(m_bdId));
		respObject.then().body("id", Matchers.greaterThan(m_minId));

		String resp = when().get(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId
				+ "/app/" + m_appId + "/contracts").body().asString();
		Type type = new TypeToken<List<com.cisco.acisizer.ui.models.ContractUi>>() {
		}.getType();
		List<com.cisco.acisizer.ui.models.ContractUi> contracts = gson.fromJson(resp, type);
		com.cisco.acisizer.ui.models.EpgUi epg3 = gson.fromJson(respObject.asString(),
				com.cisco.acisizer.ui.models.EpgUi.class);

		ArrayList<Integer> contractIds = new ArrayList<Integer>();
		for (com.cisco.acisizer.ui.models.ContractUi iter : contracts) {
			if (epg3.getId() == iter.getProviderId())
				contractIds.add(iter.getId());
		}

		for (Integer iter : contractIds) {
			String deletedContract = deleteRequest(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
					+ m_tenantId + "/app/" + m_appId + "/contract/" + iter);
			assertEquals(deletedContract, "1");

		}

		String deletedEpg = deleteRequest(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/epg/" + epg3.getId());
		assertEquals(deletedEpg, "1");
	}

	@Test
	public void addEpg() {
		createNodes();
		String deletedProj = deleteRequest(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId);
		assertEquals(deletedProj, "1");

		clearIds();

		m_projectId = createProject("proj1");
		m_tenantId = createTenant("tenant1", m_projectId);
		m_appId = createApplication("app1", m_projectId, m_tenantId);

		String bdName = ACISizerConstant.BD_DEFAULT;
		String appName = getName(
				TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps", m_appId);

		m_epgId = createEpg("epg1", appName, ACISizerConstant.DEFAULT_BD_ID, m_projectId, m_tenantId, m_appId);

		String deletedEpg = deleteRequest(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/epg/" + m_epgId);
		assertEquals(deletedEpg, "1");
	}
	

	@Test
	public void updateEpg() {
		createNodes();
		String deletedProj = deleteRequest(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId);
		assertEquals(deletedProj, "1");

		clearIds();

		m_projectId = createProject("proj1");
		m_tenantId = createTenant("tenant1", m_projectId);
		m_appId = createApplication("app1", m_projectId, m_tenantId);

		m_vrfId = createVrf("vrf1", m_projectId, m_tenantId);
		m_bdId = createBd("bd1", m_projectId, m_tenantId, m_vrfId);

		String bdName = getName(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId
				+ "/vrf/" + m_vrfId + "/bds", m_bdId);
		String appName = getName(
				TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps", m_appId);
		m_epgId = createEpg("epg1", appName, m_bdId, m_projectId, m_tenantId, m_appId);

		com.cisco.acisizer.ui.models.EpgUi udatedEpg = new com.cisco.acisizer.ui.models.EpgUi();
		udatedEpg.setBdId(m_bdId);
		udatedEpg.setSpan(4);
		udatedEpg.setName("epg3");
		udatedEpg.setAppId(m_appId);

		UIData uiData = new UIData();
		uiData.setX((float) 100);
		uiData.setY((float) 200);
		udatedEpg.setUiData(uiData);

		Response respObject = updateRequest(udatedEpg, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId
				+ "/tenant/" + m_tenantId + "/app/" + m_appId + "/epg/" + m_epgId);
		respObject.then().statusCode(HttpStatus.SC_OK);

		com.cisco.acisizer.ui.models.EpgUi respEpg = gson.fromJson(respObject.asString(),
				com.cisco.acisizer.ui.models.EpgUi.class);

		assertEquals(respEpg.getName(), udatedEpg.getName());
		assertEquals(respEpg.getSpan(), udatedEpg.getSpan());
		assertEquals(respEpg.getUiData().getX(), udatedEpg.getUiData().getX());
		assertEquals(respEpg.getUiData().getY(), udatedEpg.getUiData().getY());

		m_epgId = respEpg.getId();
		String epgName = getName(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId
				+ "/app/" + m_appId + "/epgs", m_epgId);

		assertEquals(epgName, udatedEpg.getName());
	}

	@Test
	public void addEpgWithoutFilterCount() {
		createNodes();
		String bdName = null;
		String appName = null;

		bdName = getName(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/vrf/"
				+ m_vrfId + "/bds", m_bdId);
		appName = getName(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps",
				m_appId);

		String epgName = "epg2";
		com.cisco.acisizer.ui.models.EpgUi epg = new com.cisco.acisizer.ui.models.EpgUi();
		epg.setBdId(m_bdId);
		epg.setSharedServicesEnabled(true);
		epg.setSpan(1);
		epg.setName(epgName);
		epg.setAppId(m_appId);
		epg.setSharedServiceId(m_sharedResourceId);
		epg.setNoOfsharedServiceFilter(-1);

		Response respObject = postRequest(epg, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/epg");
		respObject.then().statusCode(HttpStatus.SC_PRECONDITION_FAILED);

	}
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.EpgController#getEpg(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	
	public final void testGetEpg() throws AciEntityNotFound {
		EpgUi epg = addDummyEpgByName(project.getId(),tenant.getId(),app.getId(), EPG_NAME);
		when().get(TestingConstants.UPDATE_EPG_URI, project.getId(), tenant.getId(), app.getId(), epg.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(EPG_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.epgController#getepgs(int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */

	@Test
	public final void testGetEpgs() throws AciEntityNotFound {
		
		addDummyEpgByName(project.getId(), tenant.getId(), app.getId(), EPG_NAME);
		addDummyEpgByName(project.getId(), tenant.getId(), app.getId(), UPDATED_EPG_NAME );
		when().get(TestingConstants.GET_EPG_URI, project.getId(),tenant.getId(),app.getId()).then().statusCode(HttpStatus.SC_OK)
		.body(TestingConstants.NAME, Matchers.hasItems(EPG_NAME, UPDATED_EPG_NAME));
			
	}
	
	
	
}

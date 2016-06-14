package com.cisco.acisizer.rest.controllers;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cisco.acisizer.CiscoaciApplication;
import com.cisco.acisizer.ui.models.ApplicationConfiguration;
import com.cisco.acisizer.ui.models.ApplicationTemplate;
import com.cisco.acisizer.utils.ShadowModel;
import com.cisco.acisizer.utils.TestingConstants;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component

public class AppTemplateControllerTest extends ControllerTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//per class		
		RestAssured.port = 8090;
		System.out.println("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//per class
		System.out.println("tearDownAfterClass");
	}	
	@Before
	public void setUp() {
		projectsRepository.deleteAll();
	}
	
	@Test
	public void add3TierApplication()
	{
		//
		m_projectId = super.createProject("project11");
		int tenant1 = super.createTenantTemplate("tenant1","Local", m_projectId);

		ApplicationConfiguration configuration = new ApplicationConfiguration();
		ApplicationTemplate appTemplate = new ApplicationTemplate();
		appTemplate.setName("App");
		appTemplate.setNoOfInstances(2);
		appTemplate.setModel("3Tier");
		appTemplate.setTemplateType("app");
		configuration.setBdPolicy("Default");
		configuration.setContractComplexity("Low");
		configuration.setL3outEnabled(false);
		configuration.setEpgComplexity("Small");
		configuration.setL3outComplexity("Low");
		configuration.setSharedServiceEnabled(false);
		configuration.setSubnetPolicy("Default");

		appTemplate.setConfiguration(configuration);
		int appId = super.createApplicationTemplate(appTemplate,m_projectId,tenant1);

		List<ShadowModel> epgs = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/app/"+appId+"/epgs");
		assertTrue(epgs.size() == 3);
		
		List<ShadowModel> contracts = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/app/"+appId+"/contracts");
		assertTrue(contracts.size() == 3);
		
	}

	@Test
	public void add3TierApplicationWithSharedService()
	{
		//
		m_projectId = super.createProject("project11");
		int tenant1 = super.createTenantTemplate("tenant1","Local", m_projectId);

		ApplicationConfiguration configuration = new ApplicationConfiguration();
		ApplicationTemplate appTemplate = new ApplicationTemplate();
		appTemplate.setName("App");
		appTemplate.setNoOfInstances(1);
		appTemplate.setModel("3Tier");
		appTemplate.setTemplateType("app");
		configuration.setBdPolicy("Default");
		configuration.setContractComplexity("Low");
		configuration.setL3outEnabled(false);
		configuration.setEpgComplexity("Small");
		configuration.setL3outComplexity("Low");
		configuration.setSharedServiceEnabled(true);
		configuration.setSubnetPolicy("Default");

		appTemplate.setConfiguration(configuration);
		int appId = super.createApplicationTemplate(appTemplate,m_projectId,tenant1);

		List<ShadowModel> epgs = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/app/"+appId+"/epgs");
		assertTrue(epgs.size() == 3);

		List<ShadowModel> contracts = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/app/"+appId+"/contracts");
		assertTrue(contracts.size() == 7);
	}
	
	@Test
	public void add3TierApplicationWithUniquePolicy()
	{
		//
		m_projectId = super.createProject("project11");
		int tenant1 = super.createTenantTemplate("tenant1","Local", m_projectId);

		ApplicationConfiguration configuration = new ApplicationConfiguration();
		ApplicationTemplate appTemplate = new ApplicationTemplate();
		appTemplate.setName("App");
		appTemplate.setNoOfInstances(1);
		appTemplate.setModel("3Tier");
		appTemplate.setTemplateType("app");
		configuration.setBdPolicy("Unique");
		configuration.setContractComplexity("Low");
		configuration.setL3outEnabled(false);
		configuration.setEpgComplexity("Small");
		configuration.setL3outComplexity("Low");
		configuration.setSharedServiceEnabled(true);
		configuration.setSubnetPolicy("Unique");

		appTemplate.setConfiguration(configuration);
		int appId = super.createApplicationTemplate(appTemplate,m_projectId,tenant1);

		List<ShadowModel> epgs = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/app/"+appId+"/epgs");
		assertTrue(epgs.size() == 3);

		List<ShadowModel> contracts = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/app/"+appId+"/contracts");
		assertTrue(contracts.size() == 7);
		
		
	}
}

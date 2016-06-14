package com.cisco.acisizer.rest.controllers;

import static org.junit.Assert.*;

import java.util.List;

import javax.validation.constraints.AssertTrue;

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
import com.cisco.acisizer.utils.ShadowModel;
import com.cisco.acisizer.utils.TestingConstants;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component

public class TenantTemplateControllerTest extends ControllerTest  {

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
	public void addTenantTemplateWithLocalModel()
	{
		int vrfId = 0;
		int bdId = 0;
		int l3outId = 0;
		m_projectId = super.createProject("project11");
		assertTrue(m_projectId > m_minId);
		int tenant1 = super.createTenantTemplate("tenant1","Local", m_projectId);
		assertTrue(tenant1 > m_projectId);
		List<ShadowModel> vrfs = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/vrfs");
		for(ShadowModel iter : vrfs)
		{
			vrfId = iter.getId();
			break;
		}
		assertTrue(vrfId > tenant1);

		List<ShadowModel> bds = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/vrf/"+vrfId+"/bds");
		for(ShadowModel iter : bds)
		{
			bdId = iter.getId();
			break;
		}
		assertTrue(bdId > vrfId);

		List<ShadowModel> l3outs = getNodes(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1+"/vrf/"+vrfId+"/l3Outs");
		for(ShadowModel iter : l3outs)
		{
			l3outId = iter.getId();
			break;
		}
		assertTrue(l3outId > vrfId);
		
		String deleteTenant = super.deleteRequest(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+tenant1);
		assertEquals(deleteTenant,"1");

	}
}

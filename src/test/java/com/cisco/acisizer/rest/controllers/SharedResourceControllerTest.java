package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
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
import com.cisco.acisizer.models.Contract;
import com.cisco.acisizer.models.Epg;
import com.cisco.acisizer.models.SharedResource;
import com.cisco.acisizer.utils.TestingConstants;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component
public class SharedResourceControllerTest extends ControllerTest {

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
		
		createNodes();

	}

	@Test
	public void add() {
		
		String name = "sharedResource2";
		com.cisco.acisizer.ui.models.SharedResourceUi sr = new com.cisco.acisizer.ui.models.SharedResourceUi();
		sr.setName(name);
		Response respObject = postRequest(sr,TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/vrf/"+m_vrfId+"/sharedResource");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(name));
		respObject.then().body("id",Matchers.greaterThan(m_minId));
		
		com.cisco.acisizer.ui.models.SharedResourceUi obj = gson.fromJson(respObject.asString(),com.cisco.acisizer.ui.models.SharedResourceUi.class);

		int srId = obj.getId();
		String srName = getName(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/vrf/"+m_vrfId+"/sharedResources",srId);
	}
	
	@Test
	public void deleteSharedResourceWithEpg() {

		String bdName =  null;
		String appName = null;
		
		bdName = getName(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/vrf/"+m_vrfId+"/bds",m_bdId);
		appName = getName(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/apps",m_appId);

		String epgName = "epg2";

/*		Epg epg = new Epg(0,epgName,appName);
		epg.setBd(bdName);
		epg.setSpan(1);
		epg.setEnableSharedReource(true);
		epg.setSharedResourceId(m_sharedResourceId);
		epg.setFilterCount(4);*/
		
		com.cisco.acisizer.ui.models.EpgUi epg = new com.cisco.acisizer.ui.models.EpgUi();
		epg.setBdId(m_bdId);
		epg.setSharedServicesEnabled(true);
		epg.setSpan(1);
		epg.setName(epgName);
		epg.setAppId(m_appId);
		epg.setSharedServiceId(m_sharedResourceId);
		epg.setNoOfsharedServiceFilter(4);

		Response respObject = postRequest(epg,TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/app/"+m_appId+"/epg");		
		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(epgName));
		respObject.then().body("appId",Matchers.is(m_appId));
		respObject.then().body("bdId",Matchers.is(m_bdId));
		respObject.then().body("id",Matchers.greaterThan(m_minId));


		Response respDelete = when().delete(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/vrf/"+m_vrfId+"/sharedResource/"+m_sharedResourceId);
		respDelete.then().statusCode(HttpStatus.SC_FAILED_DEPENDENCY);


		int contractIdToBeDeleted = 0;
		String resp = when().get(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/app/"+m_appId+"/contracts").body().asString();
		Type type = new TypeToken<List<com.cisco.acisizer.ui.models.ContractUi>>(){}.getType();
		List<com.cisco.acisizer.ui.models.ContractUi> nodes = gson.fromJson(resp,type );
		for(com.cisco.acisizer.ui.models.ContractUi iter : nodes)
		{
			if(m_sharedResourceId == iter.getConsumerId())
			{
				contractIdToBeDeleted = iter.getId();
			}
		}
		
		String deleteContract = deleteRequest(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/app/"+m_appId+"/contract/"+contractIdToBeDeleted);
		assertEquals(deleteContract,"1");
		
		respDelete = when().delete(TestingConstants.PROJECT_BASE_URI+"/"+m_projectId+"/tenant/"+m_tenantId+"/vrf/"+m_vrfId+"/sharedResource/"+m_sharedResourceId);
		respDelete.then().statusCode(HttpStatus.SC_OK);

	}
}

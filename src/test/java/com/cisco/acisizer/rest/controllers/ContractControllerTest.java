package com.cisco.acisizer.rest.controllers;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cisco.acisizer.CiscoaciApplication;
import com.cisco.acisizer.models.Contract;
import com.cisco.acisizer.util.ACISizerConstant;
import com.cisco.acisizer.utils.TestingConstants;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component
public class ContractControllerTest extends ControllerTest {

	@Before
	public void setUp() {
		RestAssured.port = 8090;
		projectsRepository.deleteAll();
		createNodes();
	}

	@Test
	public void testAddContractEpg2Epg() {

		String bdName = getName(TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId
				+ "/vrf/" + m_vrfId + "/bds", m_bdId);
		String appName = getName(
				TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps", m_appId);

		int epgId2 = createEpg("epg2", appName, m_bdId, m_projectId, m_tenantId, m_appId);

		Contract ct = new Contract(0, "Contract_epg2epg", appName);
		ct.setProviderId(""+m_epgId);
		ct.setProviderType(ACISizerConstant._epg);

		ct.setConsumerId(""+epgId2);
		ct.setConsumerType(ACISizerConstant._epg);

		postRequest(ct, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/app/"
				+ m_appId + "/contract");
	}

	@Test
	public void testAddContractEpg2L3out() {

		String appName = getName(
				TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps", m_appId);

		Contract ct = new Contract(0, "Contract_epg2l3out", appName);
		ct.setProviderId(""+m_epgId);
		ct.setProviderType(ACISizerConstant._epg);

		ct.setConsumerId(""+m_l3outId);
		ct.setConsumerType(ACISizerConstant._l3out);

		postRequest(ct, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/app/"
				+ m_appId + "/contract");
	}

	@Test
	public void testAddContractEpg2Shared() {

		String appName = getName(
				TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps", m_appId);
		Contract ct = new Contract(0, "Contract_epg2l3out", appName);
		ct.setProviderId(""+m_epgId);
		ct.setProviderType(ACISizerConstant._epg);

		ct.setConsumerId(""+m_sharedResourceId);
		ct.setConsumerType(ACISizerConstant._shared_resource);

		postRequest(ct, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/app/"
				+ m_appId + "/contract");
	}

	@Test
	public void duplicateContract() {

		String appName = getName(
				TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/" + m_tenantId + "/apps", m_appId);
		String contractName = "contractDuplicate";
		Contract ct = new Contract(0, contractName, appName);
		ct.setProviderId(""+m_epgId);
		ct.setProviderType(ACISizerConstant._epg);

		ct.setConsumerId(""+m_sharedResourceId);
		ct.setConsumerType(ACISizerConstant._shared_resource);

		Response respObject = postRequest(ct, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/contract");
		respObject.then().statusCode(HttpStatus.SC_OK);

		Response respObject1 = postRequest(ct, TestingConstants.PROJECT_BASE_URI + "/" + m_projectId + "/tenant/"
				+ m_tenantId + "/app/" + m_appId + "/contract");
		respObject1.then().statusCode(HttpStatus.SC_CONFLICT);

	}

}

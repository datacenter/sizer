package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

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
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.ui.models.BdUi;
import com.cisco.acisizer.ui.models.VrfUi;
import com.cisco.acisizer.utils.TestingConstants;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

/**
 * @author Anusha
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component

public class VrfControllerTest extends ControllerTest{
	

	public static final String VRF_NAME = "vrf5";
	public static final String UPDATED_VRF_NAME = "vrf10";
	public static final String NEW_UPDATED_VRF_NAME = "vrf11";
	public static String ADD_VRF_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + VRF_NAME + "\"}";

	
	

	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	private ProjectControllerTest projectControllerTest;
	
	@Inject
	private TenantControllerTest tenantControllerTest;

	@Inject
	private Gson gson;

	private Project project;
	
	private Tenant tenant;
	
	@Before
	public void setUp() {
		RestAssured.port = 8090;
		projectsRepository.deleteAll();
		project = projectControllerTest.addDummyProject();
		tenant = tenantControllerTest.addDummyTenant(project.getId(), TenantControllerTest.ADD_TENANT_INPUT);
	}
	
	
	public VrfUi addDummyVrf(int projId, int tenantId, String name) {
		Response response = given().body(name).contentType(ContentType.JSON).when().post(TestingConstants.ADD_VRF_URI,
				projId, tenantId);
		response.then().statusCode(HttpStatus.SC_OK);

		return gson.fromJson(response.asString(), VrfUi.class);
	}

	public VrfUi addDummyVrfByName(int projId, int tenantId, String name) {
		VrfUi dummyVrf = new VrfUi();
		dummyVrf.setName(name);
		return addDummyVrf(projId, tenantId, gson.toJson(dummyVrf));
	}
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3OutController#testForDuplicateVrf(int, int, String)}
	 * .
	 */


	@Test
	public void testForDuplicateVrf() {
		addDummyVrfByName(project.getId(), tenant.getId(), TestingConstants.DUMMY);
		VrfUi dummyVrf = new VrfUi();
		dummyVrf.setName(TestingConstants.DUMMY);

		Response response = given().body(gson.toJson(dummyVrf)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_VRF_URI, project.getId(), tenant.getId());
		response.then().statusCode(HttpStatus.SC_CONFLICT);
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.VrfController#addVrf(int, int, com.cisco.acisizer.models.Vrf)}
	 * .
	 */
	@Test
	public  void testAddVrf() {
		VrfUi vrfUi=new VrfUi();
		vrfUi.setName(VRF_NAME);
		given().body(gson.toJson(vrfUi)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_VRF_URI, project.getId(), tenant.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(VRF_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.VrfController#updateVrf(int, int, int, com.cisco.acisizer.models.Vrf)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testUpdateVrf() throws AciEntityNotFound {
		VrfUi vrfUi=new VrfUi();
		vrfUi.setName(UPDATED_VRF_NAME);
		VrfUi vrf = addDummyVrfByName(project.getId(), tenant.getId(), VRF_NAME);
		given().body(gson.toJson(vrfUi)).contentType(ContentType.JSON).when()
				.put(TestingConstants.UPDATE_VRF_URI, project.getId(), tenant.getId(), vrf.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(UPDATED_VRF_NAME));
				
	}
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#deleteBd(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testDeleteVrf() throws AciEntityNotFound {
	  VrfUi vrf = addDummyVrf(project.getId(), tenant.getId(), ADD_VRF_INPUT);
		when().delete(TestingConstants.UPDATE_VRF_URI, project.getId(), tenant.getId(), vrf.getId()).then()
				.statusCode(HttpStatus.SC_OK);
		when().get(TestingConstants.UPDATE_VRF_URI, project.getId(), tenant.getId(),  vrf.getId()).then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.VrfController#getVrf(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testGetVrf() throws AciEntityNotFound {
		VrfUi vrf = addDummyVrf(project.getId(), tenant.getId(), ADD_VRF_INPUT);
		when().get(TestingConstants.UPDATE_VRF_URI, project.getId(), tenant.getId(), vrf.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(VRF_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.VrfController#getvrfs(int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */

	@Test
	public final void testGetVrfs() throws AciEntityNotFound {
		
		addDummyVrfByName(project.getId(), tenant.getId(),VRF_NAME);
		addDummyVrfByName(project.getId(), tenant.getId(),UPDATED_VRF_NAME );
		addDummyVrfByName(project.getId(), tenant.getId(),NEW_UPDATED_VRF_NAME );
		when().get(TestingConstants.GET_VRF_URI, project.getId(),tenant.getId()).then().statusCode(HttpStatus.SC_OK)
		.body(TestingConstants.NAME, Matchers.hasItems(VRF_NAME,UPDATED_VRF_NAME,NEW_UPDATED_VRF_NAME));
		
		
		
	}


	
}

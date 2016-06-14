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
import com.cisco.acisizer.ui.models.L3outUi;
import com.cisco.acisizer.ui.models.VrfUi;
import com.cisco.acisizer.utils.TestingConstants;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * @author Anusha
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component

public class L3OutControllerTest extends ControllerTest{


	public static String L3OUT_NAME = "l3out5";
	public static String UPDATED_L3OUT_NAME = "l3out10";
	public static String NEW_UPDATED_L3OUT_NAME = "l3out11";

	public static String ADD_L3OUT_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + L3OUT_NAME + "\"}";

	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	private ProjectControllerTest projectControllerTest;
	
	@Inject
	private TenantControllerTest tenantControllerTest;
	
	@Inject
	private VrfControllerTest vrfControllerTest;

	@Inject
	private Gson gson;

	private Project project;
	
	private Tenant tenant;
	
	private VrfUi vrf;

	
	@Before
	public void setUp() {
		RestAssured.port = 8090;
		projectsRepository.deleteAll();
		project = projectControllerTest.addDummyProject();
		tenant = tenantControllerTest.addDummyTenant(project.getId(), TenantControllerTest.ADD_TENANT_INPUT);
		vrf = vrfControllerTest.addDummyVrfByName(project.getId(),tenant.getId(),TestingConstants.DUMMY);

	}

	public L3outUi addDummyL3Out(int projId, int tenantId, int vrfId, String name) {
		Response response = given().body(name).contentType(ContentType.JSON).when().post(TestingConstants.ADD_L3OUT_URI,
				projId, tenantId, vrfId );
		response.then().statusCode(HttpStatus.SC_OK);

		return gson.fromJson(response.asString(), L3outUi.class);
	}

	public L3outUi addDummyL3OutByName(int projId, int tenantId,int vrfId, String name) {
		L3outUi dummyL3Out = new L3outUi();
		dummyL3Out.setName(name);
		return addDummyL3Out(projId, tenantId, vrfId, gson.toJson(dummyL3Out));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3OutController#testForDuplicateL3out(int, int, String)}
	 * .
	 */

	@Test
	public void testForDuplicateL3Out() {
		addDummyL3OutByName(project.getId(), tenant.getId(), vrf.getId(), "dummy");
		L3outUi dummyL3Out = new L3outUi();
		dummyL3Out.setName("dummy");

		Response response
		= given().body(gson.toJson(dummyL3Out)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_L3OUT_URI, project.getId(), tenant.getId(), vrf.getId());
		response.then().statusCode(HttpStatus.SC_CONFLICT);
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3OutController#addL3Out(int, int, com.cisco.acisizer.models.l3out)}
	 * .
	 */
	@Test
	public  void testAddL3Out() {
		L3outUi l3outUi=new L3outUi();
		l3outUi.setName(L3OUT_NAME);
		given().body(gson.toJson(l3outUi)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_L3OUT_URI, project.getId(), tenant.getId(), vrf.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(L3OUT_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3outController#updateL3out(int, int, int, com.cisco.acisizer.models.L3out)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testUpdateL3out() throws AciEntityNotFound {
		L3outUi l3outUi=new L3outUi();
		l3outUi.setName(UPDATED_L3OUT_NAME);
		L3outUi l3out = addDummyL3Out(project.getId(), tenant.getId(), vrf.getId(), ADD_L3OUT_INPUT);
		given().body(gson.toJson(l3outUi)).contentType(ContentType.JSON).when()
				.put(TestingConstants.UPDATE_L3OUT_URI, project.getId(), tenant.getId(), vrf.getId(), l3out.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(UPDATED_L3OUT_NAME));
				
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3outController#deletel3out(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testDeleteL3out() throws AciEntityNotFound {
	  L3outUi l3out = addDummyL3Out(project.getId(), tenant.getId(), vrf.getId(), ADD_L3OUT_INPUT);
		when().delete(TestingConstants.UPDATE_L3OUT_URI, project.getId(), tenant.getId(), vrf.getId(), l3out.getId()).then()
				.statusCode(HttpStatus.SC_OK);
		when().get(TestingConstants.UPDATE_L3OUT_URI, project.getId(), tenant.getId(),  vrf.getId(), l3out.getId()).then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3outController#getL3out(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testGetL3out() throws AciEntityNotFound {
		L3outUi l3out = addDummyL3Out(project.getId(), tenant.getId(),vrf.getId(), ADD_L3OUT_INPUT);
		when().get(TestingConstants.UPDATE_L3OUT_URI, project.getId(), tenant.getId(), vrf.getId(), l3out.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(L3OUT_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.L3outController#getL3outs(int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */

	@Test
	public final void testGetL3outs() throws AciEntityNotFound {
		
		addDummyL3OutByName(project.getId(), tenant.getId(), vrf.getId(), L3OUT_NAME);
		addDummyL3OutByName(project.getId(), tenant.getId(), vrf.getId(), UPDATED_L3OUT_NAME );
		addDummyL3OutByName(project.getId(), tenant.getId(), vrf.getId(), NEW_UPDATED_L3OUT_NAME );
		when().get(TestingConstants.GET_L3OUT_URI, project.getId(),tenant.getId(),vrf.getId()).then().statusCode(HttpStatus.SC_OK)
		.body(TestingConstants.NAME, Matchers.hasItems(L3OUT_NAME,UPDATED_L3OUT_NAME,NEW_UPDATED_L3OUT_NAME));
			
	}
	
}

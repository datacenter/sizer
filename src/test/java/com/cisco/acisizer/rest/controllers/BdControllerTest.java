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

public class BdControllerTest {
	
	public static String BD_NAME = "bd5";
	public static String UPDATED_BD_NAME = "bd10";
	public static String NEW_UPDATED_BD_NAME = "bd11";

	public static String ADD_BD_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + BD_NAME + "\"}";
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
	
	public BdUi addDummyBd(int projId, int tenantId, int vrfId, String name) {
		Response response = given().body(name).contentType(ContentType.JSON).when().post(TestingConstants.ADD_BD_URI,
				projId, tenantId, vrfId );
		response.then().statusCode(HttpStatus.SC_OK);

		return gson.fromJson(response.asString(), BdUi.class);
	}

	public BdUi addDummyBdByName(int projId, int tenantId,int vrfId, String name) {
		BdUi dummyBd = new BdUi();
		dummyBd.setName(name);
		return addDummyBd(projId, tenantId, vrfId, gson.toJson(dummyBd));
	}

	
	 /**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#testForDuplicateBd(int, int, String)}
	 * .
	 */
	@Test
	public void testForDuplicateBd() {
		addDummyBdByName(project.getId(), tenant.getId(), vrf.getId(), "dummy");
		BdUi dummyBd = new BdUi();
		dummyBd.setName("dummy");

		Response response = given().body(gson.toJson(dummyBd)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_BD_URI, project.getId(), tenant.getId(), vrf.getId());
		response.then().statusCode(HttpStatus.SC_CONFLICT);
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#addBd(int, int, com.cisco.acisizer.models.Bd)}
	 * .
	 */
	@Test
	public  void testAddBd() {
		BdUi bdUi=new BdUi();
		bdUi.setName(BD_NAME);
		given().body(gson.toJson(bdUi)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_BD_URI, project.getId(), tenant.getId(), vrf.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(BD_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#updateBd(int, int, int, com.cisco.acisizer.models.Bd)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testUpdateBd() throws AciEntityNotFound {
		BdUi bdUi=new BdUi();
		bdUi.setName(UPDATED_BD_NAME);
		BdUi bd = addDummyBd(project.getId(), tenant.getId(), vrf.getId(), ADD_BD_INPUT);
		given().body(gson.toJson(bdUi)).contentType(ContentType.JSON).when()
				.put(TestingConstants.UPDATE_BD_URI, project.getId(), tenant.getId(), vrf.getId(), bd.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(UPDATED_BD_NAME));
				
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#deleteBd(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testDeleteBd() throws AciEntityNotFound {
	  BdUi bd = addDummyBd(project.getId(), tenant.getId(), vrf.getId(), ADD_BD_INPUT);
		when().delete(TestingConstants.UPDATE_BD_URI, project.getId(), tenant.getId(), vrf.getId(), bd.getId()).then()
				.statusCode(HttpStatus.SC_OK);
		when().get(TestingConstants.UPDATE_BD_URI, project.getId(), tenant.getId(),  vrf.getId(), bd.getId()).then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BdController#getBd(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testGetBd() throws AciEntityNotFound {
		BdUi bd = addDummyBd(project.getId(), tenant.getId(),vrf.getId(), ADD_BD_INPUT);
		when().get(TestingConstants.UPDATE_BD_URI, project.getId(), tenant.getId(), vrf.getId(), bd.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(BD_NAME));
	}
	
	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.BDController#getBds(int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */

	@Test
	public final void testGetBds() throws AciEntityNotFound {
		
		addDummyBdByName(project.getId(), tenant.getId(), vrf.getId(), BD_NAME);
		addDummyBdByName(project.getId(), tenant.getId(), vrf.getId(), UPDATED_BD_NAME );
		addDummyBdByName(project.getId(), tenant.getId(), vrf.getId(), NEW_UPDATED_BD_NAME );
		when().get(TestingConstants.GET_BD_URI, project.getId(),tenant.getId(),vrf.getId()).then().statusCode(HttpStatus.SC_OK)
		.body(TestingConstants.NAME, Matchers.hasItems(BD_NAME,UPDATED_BD_NAME,NEW_UPDATED_BD_NAME));
			
	}


}

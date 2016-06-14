/**
 * 
 */
package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertNotNull;

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
import com.cisco.acisizer.models.Project;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.util.ACISizerConstant;
import com.cisco.acisizer.utils.TestingConstants;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

/**
 * @author Mahesh
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CiscoaciApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
@Component
public class TenantControllerTest {
	public static final String ACISIZER_V1_PROJECT = "/acisizer/v1/project/";
	public static final String USER = "user";
	public static final String ADD_TENANT_INPUT = "{\"" + TestingConstants.DISPLAY_NAME + "\":\"" + USER + "\"}";
	public static final String UPDATED_USER_NAME = "updatedUser";
	public static final String UPDATE_TENANT_INPUT = "{\"" + TestingConstants.DISPLAY_NAME + "\":\"" + UPDATED_USER_NAME
			+ "\"}";

	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	private ProjectControllerTest projectControllerTest;

	@Inject
	private Gson gson;

	private Project project;

	@Before
	public void setUp() {
		RestAssured.port = 8090;
		projectsRepository.deleteAll();
		project = projectControllerTest.addDummyProject();
		
	}

	@Test
	public void testProjectControllerTest() {
		assertNotNull(projectControllerTest);
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#addTenant(int, com.cisco.acisizer.models.Tenant)}
	 * .
	 */
	
	@Test
	public final void testAddTenant() {
		given().body(ADD_TENANT_INPUT).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_TENANT_URI, project.getId()).then().statusCode(HttpStatus.SC_OK);
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#updateTenant(int, int, com.cisco.acisizer.models.Tenant)}
	 * .
	 */
	@Test
	public final void testUpdateTenant() {
		Tenant tenant = addDummyTenant(project.getId(), UPDATE_TENANT_INPUT);

		Response response = given().body(UPDATE_TENANT_INPUT).contentType(ContentType.JSON).when()
				.put(TestingConstants.UPDATE_TENANT_URI, project.getId(), tenant.getId());
		response.then().statusCode(HttpStatus.SC_OK).body(TestingConstants.DISPLAY_NAME,
				Matchers.is(UPDATED_USER_NAME));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#deleteTenant(int, int)}
	 * .
	 */
	@Test
	public final void testDeleteTenant() {
		Tenant tenant = addDummyTenant(project.getId(), ADD_TENANT_INPUT);

		when().delete(TestingConstants.UPDATE_TENANT_URI, project.getId(), tenant.getId()).then()
				.statusCode(HttpStatus.SC_OK);
		when().get(TestingConstants.UPDATE_TENANT_URI, project.getId(), tenant.getId()).then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#getTenant(int, int)}
	 * .
	 */
	@Test
	public final void testGetTenant() {
		Tenant tenant = addDummyTenant(project.getId(), ADD_TENANT_INPUT);

		when().get(TestingConstants.UPDATE_TENANT_URI, project.getId(), tenant.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.DISPLAY_NAME, Matchers.is(USER));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#getTenants(int)}
	 * .
	 */
	@Test
	public final void testGetTenants() {

		addDummyTenant(project.getId(), ADD_TENANT_INPUT);
		addDummyTenant(project.getId(), UPDATE_TENANT_INPUT);

		when().get(TestingConstants.GET_TENANTS_URI, project.getId()).then().statusCode(HttpStatus.SC_OK)
				.body(TestingConstants.DISPLAY_NAME, Matchers.hasItems(USER, UPDATED_USER_NAME));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#getNodeCollectionTenant(int, int)}
	 * .
	 */
	@Test
	public final void testGetNodeCollectionTenant() {
		Response response = when().get(TestingConstants.NODE_COLLECTION_URI, project.getId(),
				TestingConstants.COMMON_TENANT_ID);
		response.then().statusCode(HttpStatus.SC_OK).body(TestingConstants.NODES,
				Matchers.hasSize(TestingConstants.NODE_COLLECTION_SIZE));
		response.then().body(TestingConstants.TYPE, Matchers.hasItems(ACISizerConstant._l3out,
				ACISizerConstant._shared_resource, ACISizerConstant._bd, ACISizerConstant._vrf));

	}
	
	
	/**
	 * Add method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#addingDummyTenant(int,String)}
	 * .
	 */

	public Tenant addDummyTenant(int projId, String dummyTenant) {
		Response responxe = given().body(dummyTenant).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_TENANT_URI, projId);
		responxe.then().statusCode(HttpStatus.SC_OK);

		return gson.fromJson(responxe.asString(), Tenant.class);
	}

	public Tenant addDummyTenantByName(int projId, String name) {
		Tenant dummyTenant = new Tenant(1, name);
		return addDummyTenant(projId, gson.toJson(dummyTenant));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.TenantController#duplicateTenant(String,String)}
	 * .
	 */
	@Test
	public void testForDuplicateTenant() {
		addDummyTenantByName(project.getId(), "dummy");
		Tenant dummyTenant = new Tenant(1, "dummy");

		Response responxe = given().body(gson.toJson(dummyTenant)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_TENANT_URI, project.getId());
		responxe.then().statusCode(HttpStatus.SC_CONFLICT);
	}
}

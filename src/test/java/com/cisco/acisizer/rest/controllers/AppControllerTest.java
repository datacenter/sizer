/**
 * 
 */
package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.fail;

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
import com.cisco.acisizer.ui.models.ApplicationUi;
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
public class AppControllerTest {

	public static final int GET_APPS_SIZE = 2;
	public static final String EMPTY_STRING = "";
	public static String UPDATED_APP_NAME = "app10";
	public static String APP_NAME = "app9";
	public static int UPDATED_INSTANCES = 10;
	public static String NO_OF_INSTANCES = "noOfInstances";
	public static String ADD_APP_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + APP_NAME + "\",\"" + NO_OF_INSTANCES
			+ "\":2}";
	public static String UPDATE_APP_INPUT = "{\"" + TestingConstants.NAME + "\": \"" + UPDATED_APP_NAME + "\",\""
			+ NO_OF_INSTANCES + "\":" + UPDATED_INSTANCES + "}";

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

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#addApp(int, int, com.cisco.acisizer.models.Application)}
	 * .
	 */
	@Test
	public final void testAddApp() {
		given().body(ADD_APP_INPUT).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_APP_URI, project.getId(), tenant.getId()).then()
				.statusCode(HttpStatus.SC_OK);
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#updateApp(int, int, int, com.cisco.acisizer.models.Application)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testUpdateApp() throws AciEntityNotFound {
		com.cisco.acisizer.ui.models.ApplicationUi app = addDummyApp(project.getId(), tenant.getId(), ADD_APP_INPUT);
		given().body(UPDATE_APP_INPUT).contentType(ContentType.JSON).when()
				.put(TestingConstants.UPDATE_APP_URI, project.getId(), tenant.getId(), app.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(UPDATED_APP_NAME))
				.body(NO_OF_INSTANCES, Matchers.is(UPDATED_INSTANCES));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#deleteApp(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testDeleteApp() throws AciEntityNotFound {
		com.cisco.acisizer.ui.models.ApplicationUi app = addDummyApp(project.getId(), tenant.getId(), ADD_APP_INPUT);
		when().delete(TestingConstants.UPDATE_APP_URI, project.getId(), tenant.getId(), app.getId()).then()
				.statusCode(HttpStatus.SC_OK);
		when().get(TestingConstants.UPDATE_APP_URI, project.getId(), tenant.getId(), app.getId()).then()
				.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#getApp(int, int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */
	@Test
	public final void testGetApp() throws AciEntityNotFound {
		com.cisco.acisizer.ui.models.ApplicationUi app = addDummyApp(project.getId(), tenant.getId(), ADD_APP_INPUT);
		when().get(TestingConstants.UPDATE_APP_URI, project.getId(), tenant.getId(), app.getId()).then()
				.statusCode(HttpStatus.SC_OK).body(TestingConstants.NAME, Matchers.is(APP_NAME));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#getApps(int, int)}
	 * .
	 * 
	 * @throws AciEntityNotFound
	 */

	@Test
	public final void testGetApps() throws AciEntityNotFound {
		addDummyApp(project.getId(), tenant.getId(), ADD_APP_INPUT);
		addDummyApp(project.getId(), tenant.getId(), UPDATE_APP_INPUT);
		when().get(TestingConstants.GET_APPS_URI, project.getId(), tenant.getId()).then().statusCode(HttpStatus.SC_OK)
				.body(EMPTY_STRING, Matchers.hasSize(GET_APPS_SIZE))
				.body(TestingConstants.NAME, Matchers.hasItems(APP_NAME, UPDATED_APP_NAME));
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#getNodeCollection(int, int, int)}
	 * .
	 */
	@Test
	public final void testGetNodeCollection() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#getAppFabricNodeCollection(int, int, int)}
	 * .
	 */
	@Test
	public final void testGetAppFabricNodeCollection() {
		fail("Not yet implemented"); // TODO
	}

	public ApplicationUi addDummyApp(int projId, int tenantId, String app) {
		Response response = given().body(app).contentType(ContentType.JSON).when().post(TestingConstants.ADD_APP_URI,
				projId, tenantId);
		return gson.fromJson(response.asString(), ApplicationUi.class);
	}

	public ApplicationUi addDummyAppByName(int projId, int tenantId, String name) {
		ApplicationUi dummyApp = new ApplicationUi();
		dummyApp.setName(name);
		return addDummyApp(projId, tenantId, gson.toJson(dummyApp));
	}
	

	/**
	 * Test method for
	 * {@link com.cisco.acisizer.rest.controllers.AppController#testForDuplicateApp(int, int, String)}
	 * .
	 */

	@Test
	public void testForDuplicateApp() {
		addDummyAppByName(project.getId(), tenant.getId(), "dummy");
		ApplicationUi dummyApp = new ApplicationUi();
		dummyApp.setName("dummy");

		Response responxe = given().body(gson.toJson(dummyApp)).contentType(ContentType.JSON).when()
				.post(TestingConstants.ADD_APP_URI, project.getId(), tenant.getId());
		responxe.then().statusCode(HttpStatus.SC_CONFLICT);
	}

}

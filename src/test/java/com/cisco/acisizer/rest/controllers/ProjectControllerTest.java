/**
 * 
 */
package com.cisco.acisizer.rest.controllers;

import static com.jayway.restassured.RestAssured.given;
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
import com.cisco.acisizer.repo.ProjectsRepository;
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
public class ProjectControllerTest {

	public static final String UPDATE_PROJECT_URI=TestingConstants.PROJECT_BASE_URI+"/{id}";
	
	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	private Gson gson;

	@Before
	public void setUp() {
		RestAssured.port = 8090;
		projectsRepository.deleteAll();
	}

	@Test
	public void testAddProjectPositiveScenario() {
		given().body(TestingConstants.ADD_PROJECT_INPUT).contentType(ContentType.JSON).when().post(TestingConstants.PROJECT_BASE_URI).then()
				.statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testAddProjectForNotNull(){
		Response response=given().body(TestingConstants.ADD_PROJECT_INPUT).contentType(ContentType.JSON).when().post(TestingConstants.PROJECT_BASE_URI);
		assertNotNull(response);
	}

	@Test
	public void testAddProjectNegativeScenario() {
		addDummyProject();
		given().body(TestingConstants.ADD_PROJECT_INPUT).contentType(ContentType.JSON).when().post(TestingConstants.PROJECT_BASE_URI).then()
				.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

	}

	@Test
	public void testUpdateProjectPositiveScenario() {
		Project projId = addDummyProject();
		given().body(TestingConstants.UPDATE_PROJECT_INPUT).contentType(ContentType.JSON).when().put(UPDATE_PROJECT_URI, projId.getId())
				.then().statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void testUpdateProjectNegativeScenario() {
		addDummyProject();
		given().body(TestingConstants.UPDATE_PROJECT_INPUT).contentType(ContentType.JSON).when().put(UPDATE_PROJECT_URI, -1)
				.then().statusCode(HttpStatus.SC_NOT_FOUND);
	}

	public Project addDummyProject() {
	
			Response response=given().body(TestingConstants.ADD_PROJECT_INPUT).contentType(ContentType.JSON).when().post(TestingConstants.PROJECT_BASE_URI);
			response.then().statusCode(HttpStatus.SC_OK);
			Project proj=gson.fromJson(response.asString(), Project.class);
			assertNotNull(proj);
		
		return proj;
	}

	@Test
	public void addProjectCheckType()
	{
		projectsRepository.deleteAll();
		
		String projName = "project1";
		Project project = new Project();
		project.setType(null);
		project.setName(projName);
		Response respObject =	given().body(gson.toJson(project)).contentType(ContentType.JSON).when().post(TestingConstants.PROJECT_BASE_URI);

		respObject.then().statusCode(HttpStatus.SC_OK);
		respObject.then().body("name",Matchers.is(projName));
		respObject.then().body("id",Matchers.greaterThan(100));
		respObject.then().body("type",Matchers.is("ACI"));
	}
}

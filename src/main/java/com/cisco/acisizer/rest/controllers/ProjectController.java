/**
 * 
 */
package com.cisco.acisizer.rest.controllers;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.ProjectTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.AciFileIoException;
import com.cisco.acisizer.exceptions.AciSizingException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.exceptions.ProjectNameExistsException;
import com.cisco.acisizer.exceptions.ProjectTypeInvalidException;
import com.cisco.acisizer.models.Project;
import com.cisco.acisizer.models.Tenant;
import com.cisco.acisizer.services.ProjectServices;
import com.cisco.acisizer.summary.SummaryUIResponse;
import com.cisco.acisizer.ui.models.LeafChoiceUi;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Deepa
 *
 */

@RestController
public class ProjectController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Inject
	private ProjectServices projectServices;

	private String getUserId(HttpServletRequest request) {
		String userId = request.getHeader("auth_user");
		if (null == userId) {
			userId = request.getHeader("http_auth_user");
			if (null == userId)
				userId = "dummy";
		}

		System.out.println("The logged in user : " + userId);

		return userId;
	}

	@ApiOperation(httpMethod = "POST", value = "Add a Project")
	@RequestMapping(value = "/acisizer/v1/project", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Project addProject(HttpServletRequest request, @RequestBody ProjectTable project)
			throws ProjectTypeInvalidException, ProjectNameExistsException {
		return projectServices.addProject(getUserId(request), project);
	}

	@ApiOperation(httpMethod = "PUT", value = "Updates a Project")
	@RequestMapping(value = "/acisizer/v1/project/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public Project updateProject(HttpServletRequest request, @PathVariable("id") int projectId,
			@RequestBody ProjectTable project) throws AciEntityNotFound, ProjectNameExistsException {
		return projectServices.updateProject(project, projectId);
	}

	@ApiOperation(httpMethod = "DELETE", value = "deletes a Project")
	@RequestMapping(value = "/acisizer/v1/project/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public int deleteProject(HttpServletRequest request, @PathVariable("id") int projectId) throws AciEntityNotFound {
		return projectServices.deleteProject(projectId);
	}

	@ApiOperation(httpMethod = "GET", value = "Gets a Project")
	@RequestMapping(value = "/acisizer/v1/project/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Project getProject(HttpServletRequest request, @PathVariable("id") int projectId)
			throws AciEntityNotFound, AciFileIoException, AciSizingException {
		return projectServices.getProject(projectId);
	}

	@ApiOperation(httpMethod = "GET", value = "Gets a Project sizing results")
	@RequestMapping(value = "/acisizer/v1/project/{id}/sizingresults", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Project getProjectSizingResults(HttpServletRequest request, @PathVariable("id") int projectId)
			throws AciEntityNotFound, AciFileIoException, AciSizingException {
		return projectServices.getSizingREsults(projectId);
	}

	@ApiOperation(httpMethod = "GET", value = "Gets all Project")
	@RequestMapping(value = "/acisizer/v1/projects", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Project> getProjects(HttpServletRequest request) {
		logServerInfo();
		return projectServices.getProjects(getUserId(request));
	}

	private void logServerInfo() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		LOGGER.info(("Current relative path is: " + s));

		LOGGER.info("user.dir: " + System.getProperty("user.dir"));
		LOGGER.info(new File(System.getProperty("catalina.base")).getAbsoluteFile().toString());
		InetAddress ip;
		String hostname;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			LOGGER.info("Your current IP address : " + ip);
			LOGGER.info("Your current Hostname : " + hostname);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@ApiOperation(httpMethod = "GET", value = "sizing summary")
	@RequestMapping(value = "/acisizer/v1/project/{id}/sizingsummary", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SummaryUIResponse getSizingSummary(HttpServletRequest request, @PathVariable("id") int projectId)
			throws AciEntityNotFound, AciFileIoException, AciSizingException {
		return projectServices.getSizingSummary(projectId);
	}

	@RequestMapping(value = "/acisizer/v1/project/{projectId}/nodeCollection/defaultValues", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Tenant getDefaultValueTenant(HttpServletRequest request, @PathVariable("projectId") int projectId)
			throws AciEntityNotFound {
		return projectServices.getDefaultValueTenant(projectId);
	}

	@ApiOperation(httpMethod = "POST", value = "clone a Project")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/clone", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Project createClone(HttpServletRequest request, @PathVariable("projectId") int projectId) {
		return projectServices.createClone(getUserId(request), projectId);
	}

	@ApiOperation(httpMethod = "GET", value = "Search all projects with respect to the search keys")
	@RequestMapping(value = "/acisizer/v1/projectQuery", params = { "query", "pageNumber",
			"pageSize" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Project> searchProjects(HttpServletRequest request,
			@RequestParam(value = "query", required = true) String query,
			@RequestParam(value = "pageNumber", required = true) int pageNumber,
			@RequestParam(value = "pageSize", required = true) int pageSize) {
		return projectServices.searchProjects(getUserId(request), query, pageNumber, pageSize);
	}

	@ApiOperation(httpMethod = "GET", value = "Gets all rooms aggregated  inventory of the project ")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/physical/inventory", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public InventoryInfo aggInventory(HttpServletRequest request, @PathVariable("projectId") int projectId)
			throws AciEntityNotFound {
		return projectServices.getAllRoomsInventory(projectId);
	}

	@ApiOperation(httpMethod = "POST", value = "Add leaf choice")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/preferredswitch", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void getChoice(@PathVariable("projectId") int projectId, @RequestBody LeafChoiceUi leafChoiceUi)
			throws AciEntityNotFound, GenericInvalidDataException {

		projectServices.postPreferredSwitch(projectId, leafChoiceUi);

	}

}

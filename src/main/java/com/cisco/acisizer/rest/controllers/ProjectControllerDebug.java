/**
 * 
 */
package com.cisco.acisizer.rest.controllers;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.services.ProjectServices;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Mahesh
 *
 */
@RestController
@Profile({"dev","stage"})
public class ProjectControllerDebug {

	@Inject
	ProjectServices projectServices;

	@ApiOperation(httpMethod = "GET", value = "sizing summary input")
	@RequestMapping(value = "/acisizer/v1/project/{id}/sizingsummaryinput", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getSizingSummary(@PathVariable("id") int projectId) throws AciEntityNotFound {

		return projectServices.getSizingSummaryInput(projectId);
	}
}

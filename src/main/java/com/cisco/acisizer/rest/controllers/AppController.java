/**
 * Copyright @maplelabs
 */
package com.cisco.acisizer.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.ForbiddenOperationException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.models.Application;
import com.cisco.acisizer.services.AppServices;
import com.cisco.acisizer.ui.models.ApplicationTemplate;
import com.cisco.acisizer.view.ViewAppFabricNodes;
import com.cisco.acisizer.view.ViewNode;
import com.cisco.acisizer.view.ViewNodes;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Mahesh
 *
 */

@RestController
public class AppController extends AciController {

	@Autowired
	private AppServices m_service;

	@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict, item already exists"),
			@ApiResponse(code = 507, message = "Insufficient Storage, could not save") })

	@ApiOperation(value = "Add an app to tenant", notes = "Only single app can be added with the call, the app name is expected to be unique for this tenant", response = Application.class, responseContainer = "Self")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/app", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public com.cisco.acisizer.ui.models.ApplicationUi addApp(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId,
			@Valid @RequestBody com.cisco.acisizer.ui.models.ApplicationUi app) throws AciEntityNotFound, ForbiddenOperationException {
		com.cisco.acisizer.ui.models.ApplicationUi respApp = m_service.addApp(projectId, tenantId, app);
		return respApp;
	}

	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })

	@ApiOperation(value = "Update the app", notes = "Update the app by providing project id, tenant id and app id", response = Application.class, responseContainer = "Self")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/app/{appId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public com.cisco.acisizer.ui.models.ApplicationUi updateApp(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId,
			@PathVariable("appId") int appId, @Valid @RequestBody com.cisco.acisizer.ui.models.ApplicationUi app) throws AciEntityNotFound {

		com.cisco.acisizer.ui.models.ApplicationUi respApp = m_service.updateApp(projectId, tenantId, appId, app);
		return respApp;

	}

	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })

	@ApiOperation(value = "Delete the app", notes = "Delete the app by providing project id, tenant id and app id")

	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/app/{appId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public int deleteApp(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId,
			@PathVariable("appId") int appId) throws AciEntityNotFound {
		return (m_service.deleteApp(projectId, tenantId, appId));
	}

	@ApiOperation(value = "Get the app info", notes = "Get the app info by providing project id, tenant id and app id", response = Application.class)
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/app/{appId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public com.cisco.acisizer.ui.models.ApplicationUi getApp(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId,
			@PathVariable("appId") int appId) throws AciEntityNotFound {

		com.cisco.acisizer.ui.models.ApplicationUi respApp = m_service.getApp(projectId, tenantId, appId);
		return respApp;

	}

	@ApiOperation(value = "Get all apps", notes = "Get all app details for a particular tenant by providing project id and tenant id", response = Application.class, responseContainer = "List")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/apps", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<com.cisco.acisizer.ui.models.ApplicationUi> getApps(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId)
			throws AciEntityNotFound {
		List<com.cisco.acisizer.ui.models.ApplicationUi> respApps = m_service.getApps(projectId, tenantId);
		return respApps;

	}

	@ApiOperation(value = "Obsolete : Get the app nodes", notes = "Get the nodes with respect to the app by providing project id, tenant id and app id", response = ViewNode.class)
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/app/{appId}/nodeCollection_obsolete", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ViewNodes getNodeCollection(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId,
			@PathVariable("appId") int appId) throws AciEntityNotFound {

		ViewNodes nodes = m_service.getNodeCollection(projectId, tenantId, appId);
		return nodes;

	}

	@ApiOperation(value = "Get the app and fabric nodes", notes = "Get the nodes with respect to the tenant and app by providing project id, tenant id and app id", response = ViewAppFabricNodes.class)
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/app/{appId}/nodeCollection", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ViewAppFabricNodes getAppFabricNodeCollection(@PathVariable("projectId") int projectId,
			@PathVariable("tenantId") int tenantId, @PathVariable("appId") int appId) throws AciEntityNotFound {

		return m_service.getAppFabricNodeCollection(projectId, tenantId, appId);
	}

	@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict, item already exists"),
			@ApiResponse(code = 507, message = "Insufficient Storage, could not save") })

	@ApiOperation(value = "Add an app to tenant", 
	notes = "Only single app can be added with the call, the app name is expected to be unique for this tenant"
			+ ", model = NoTemplate/Flat/2Tier/3Tier"
			+ ", templateType = app"
			+ ", epgComplexity = Small/Medium/Large"
			+ ", contractComplexity = Low/Medium/High"
			+ ", l3outComplexity = Low/Medium/High"
			+ ", subnetPolicy = Default/Unique"
			+ ", bdPolicy = Default/Unique", 
	response = Application.class, responseContainer = "Self")
	@RequestMapping(value = "/acisizer/v1/project/{projectId}/tenant/{tenantId}/appTemplate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public com.cisco.acisizer.ui.models.ApplicationUi addAppTemplate(@PathVariable("projectId") int projectId, @PathVariable("tenantId") int tenantId,
			@Valid @RequestBody ApplicationTemplate appTemplate) throws AciEntityNotFound, GenericInvalidDataException, ForbiddenOperationException {
		return m_service.addAppTemplate(projectId, tenantId, appTemplate);
	}

}

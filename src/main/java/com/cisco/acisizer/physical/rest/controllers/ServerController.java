package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.acisizer.domain.ServerTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.physical.rest.models.ServerUi;
import com.cisco.acisizer.physical.services.ServerServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.rest.controllers.AciController;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(AciPhysicalSizerConstants.SERVER_URL)
public class ServerController extends AciController {
	
	@Inject
	ServerServices serverServices;
	
	@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict, item already exists"),
			@ApiResponse(code = 507, message = "Insufficient Storage, could not save") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ServerTable addServer(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId, @Valid @RequestBody ServerUi serverUi) throws AciEntityNotFound, GenericInvalidDataException
	{
		return serverServices.addServer(projectId,roomId,rowId,rackId,serverUi);
	}

	
	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })
	@RequestMapping(value = "/{serverId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ServerTable updateServer(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId, @PathVariable("serverId") int serverId, @Valid @RequestBody ServerUi serverUi)
			 throws AciEntityNotFound,GenericCouldNotSaveException, GenericInvalidDataException
	{
		return serverServices.updateServer(projectId,roomId,rowId,rackId,serverId,serverUi);
	}
	
	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })
	@RequestMapping(value = "/{serverId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public ServerTable deleteServer(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId, @PathVariable("serverId") int serverId)
			throws AciEntityNotFound,GenericCouldNotSaveException
	{
		return serverServices.deleteServer(projectId,roomId,rowId,rackId,serverId);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ServerTable> getServers(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId)
			throws AciEntityNotFound
	{
		return serverServices.getServers(projectId,roomId,rowId,rackId);
	}
}

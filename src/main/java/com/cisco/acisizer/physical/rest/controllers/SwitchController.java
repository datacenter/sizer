/**
 * 
 */
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

import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.physical.rest.models.SwitchUi;
import com.cisco.acisizer.physical.services.SwitchServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @author Mahesh
 *
 */
@RestController
@RequestMapping(AciPhysicalSizerConstants.SWITCH_URL)
public class SwitchController {

	@Inject
	private SwitchServices switchServices;

	@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict, item already exists"),
			@ApiResponse(code = 507, message = "Insufficient Storage, could not save") })
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public SwitchTable addSwitch(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId, @Valid @RequestBody SwitchUi switchUi)
			throws AciEntityNotFound, GenericCouldNotSaveException, GenericInvalidDataException {
		return switchServices.addSwitch(projectId, roomId, rowId, rackId, switchUi);
	}

	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })
	@RequestMapping(value = "/{switchId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public SwitchTable updateSwitch(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId,
			@PathVariable("switchId") int switchId, @Valid @RequestBody SwitchUi switchUi)
			throws AciEntityNotFound, GenericCouldNotSaveException {
		return switchServices.updateSwitch(projectId, roomId, rowId, rackId, switchId, switchUi);
	}

	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })
	@RequestMapping(value = "/{switchId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public SwitchTable deleteSwitch(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId,
			@PathVariable("switchId") int switchId) throws AciEntityNotFound, GenericCouldNotSaveException {
		return switchServices.deleteSwitch(projectId, roomId, rowId, rackId, switchId);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SwitchTable> getSwitches(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId) throws AciEntityNotFound {
		return switchServices.getSwitches(projectId, roomId, rowId, rackId);
	}

	@RequestMapping(value = "/round", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public void deleteSwitchByRound(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId) throws AciEntityNotFound, GenericCouldNotSaveException {
		switchServices.deleteSwitchByRound(rowId);
	}
}

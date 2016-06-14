package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.physical.rest.models.RackUi;
import com.cisco.acisizer.physical.rest.models.View;
import com.cisco.acisizer.physical.services.RackServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@RequestMapping(AciPhysicalSizerConstants.RACK_URL)
public class RackController {
	@Inject
	private RackServices rackServices;

	@RequestMapping(value = "/{rackId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	@JsonView(View.Rows.class)
	public RackTable deleteRack(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId) {
		return rackServices.deleteRack(projectId, roomId, rowId, rackId);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	@JsonView(View.Rows.class)
	public List<RackTable> getRacks(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId) {
		return rackServices.getAllRacks(projectId, roomId, rowId);
	}
	
	@JsonView(View.Rows.class)
	@RequestMapping(value = "/{rackId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	RackTable updateRack(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @PathVariable("rackId") int rackId, @RequestBody RackUi rackUi) throws AciEntityNotFound, GenericInvalidDataException
	{
		return rackServices.updateRack(projectId, roomId, rowId, rackId, rackUi);
	}
}

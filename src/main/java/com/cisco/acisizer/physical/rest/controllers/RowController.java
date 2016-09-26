/**
 * 
 */
package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.UnterminatedRacks;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.physical.rest.models.RowUi;
import com.cisco.acisizer.physical.rest.models.SwitchUi;
import com.cisco.acisizer.physical.services.RowServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Controller
@RequestMapping(AciPhysicalSizerConstants.ROW_URL)
public class RowController {

	@Inject
	private RowServices rowServices;

	@JsonView(View.Rows.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<RowTable> getRooms(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId) {
		return rowServices.getRows(projectId, roomId);
	}

	@JsonView(View.Rows.class)
	@RequestMapping(value = "/{rowId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public RowTable deleteRow(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId) {
		return rowServices.deleteRow(projectId, roomId, rowId);
	}

	@JsonView(View.Rows.class)
	@RequestMapping(value = "/{rowId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public RowTable updateRow(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @RequestBody RowUi rowUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException {
		return rowServices.updateRow(projectId, roomId, rowId, rowUi);
	}

	@JsonView(View.Rows.class)
	@RequestMapping(value = "/{rowId}/terminate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<UnterminatedRacks> terminateRow(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId, @QueryParam("isManual") boolean isManual, @RequestBody SwitchUi switchUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException, TerminationException {
		return rowServices.terminateRow(projectId, roomId, rowId, isManual, switchUi);
	}

	@JsonView(View.Rows.class)
	@RequestMapping(value = "/{rowId}/revert", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void deleteRound(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@PathVariable("rowId") int rowId)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException {
		rowServices.deleteSwitchByRound(projectId, roomId, rowId);
	}

}

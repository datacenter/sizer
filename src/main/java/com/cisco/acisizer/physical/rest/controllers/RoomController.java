/**
 * 
 */
package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.physical.rest.models.RoomRevertUi;
import com.cisco.acisizer.physical.rest.models.RoomTerminationUi;
import com.cisco.acisizer.physical.rest.models.RoomUi;
import com.cisco.acisizer.physical.rest.models.View;
import com.cisco.acisizer.physical.services.RoomServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Mahesh
 *
 */
@Controller
@RequestMapping(AciPhysicalSizerConstants.ROOM_URL)
public class RoomController {

	@Inject
	private RoomServices roomServices;

	@JsonView(View.Room.class)
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public RoomTable addRoom(@PathVariable("id") int projectId, @RequestBody RoomUi roomUi)
			throws AciEntityNotFound, GenericInvalidDataException {
		return roomServices.addRoom(projectId, roomUi);
	}

	@JsonView(View.Room.class)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<RoomTable> getRooms(@PathVariable("id") int projectId) {
		return roomServices.getRooms(projectId);
	}

	@JsonView(View.Room.class)
	@RequestMapping(value = "/{roomId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public RoomTable deleteRoom(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId) {
		return roomServices.deleteRoom(projectId, roomId);
	}

	@JsonView(View.Room.class)
	@RequestMapping(value = "/{roomId}/clone", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public RoomTable cloneRoom(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId) {
		return roomServices.roomClone(roomId);
	}

	@JsonView(View.Room.class)
	@RequestMapping(value = "/{roomId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public RoomTable updateRoom(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@RequestBody RoomUi roomUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException {
		return roomServices.updateRoom(projectId, roomId, roomUi);
	}

	@JsonView(View.Room.class)
	@RequestMapping(value = "/{roomId}/terminate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public boolean terminateRoom(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@RequestBody RoomTerminationUi roomUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException, TerminationException {
		return roomServices.terminateRoom(projectId, roomId, roomUi);
	}

	@JsonView(View.Room.class)
	@RequestMapping(value = "/{roomId}/revert", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void revertRoom(@PathVariable("id") int projectId, @PathVariable("roomId") int roomId,
			@RequestBody RoomRevertUi roomUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException {
		roomServices.revertRoom(projectId, roomId, roomUi);
	}

}

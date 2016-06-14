/**
 * 
 */
package com.cisco.acisizer.physical.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.PolicyTable;
import com.cisco.acisizer.domain.ProjectTable;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RackTypeTable;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.domain.UnterminatedRacks;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.helper.RoomHelper;
import com.cisco.acisizer.physical.log.models.RoomLog;
import com.cisco.acisizer.physical.rest.models.RoomRevertUi;
import com.cisco.acisizer.physical.rest.models.RoomTerminationUi;
import com.cisco.acisizer.physical.rest.models.RoomUi;
import com.cisco.acisizer.physical.sizing.algo.SpineTerminationAlgo;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.DeviceTypeRepository;
import com.cisco.acisizer.repo.PolicyRepository;
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.repo.RackRepository;
import com.cisco.acisizer.repo.RackTypeRepository;
import com.cisco.acisizer.repo.RoomRepository;
import com.cisco.acisizer.repo.SwitchRepository;
import com.cisco.acisizer.util.ACISizerConstant;

/**
 * @author Mahesh
 *
 */
@Service
public class RoomServices {

	public static final String ROW_PREFIX = "Row-";

	public static final String ROOM_PREFIX = "Room-";

	public static final String ROOM_1 = "Room-1";

	@Inject
	private RoomRepository roomRepository;

	@Inject
	private RackTypeRepository rackTypeRepository;

	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	PolicyRepository policyRepository;

	@Inject
	private RoomHelper roomHelper;

	@Inject
	private RowServices rowServices;

	@Inject
	private SpineTerminationAlgo spineTerminationAlgo;

	@Inject
	private DeviceTypeRepository deviceTypeRepository;

	@Inject
	private RackRepository rackRepository;

	@Inject
	private SwitchRepository switchRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServices.class);

	public static final ThreadLocal<RoomLog> localRoom = new ThreadLocal<RoomLog>() {
		@Override
		protected RoomLog initialValue() {
			return new RoomLog();
		}
	};

	public RoomTable addRoom(int projId, RoomUi roomUi) throws AciEntityNotFound, GenericInvalidDataException {

		ProjectTable projectTable = projectsRepository.findOne(projId);
		if (null == projectTable) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}
		RackTypeTable rackType = rackTypeRepository.findOne(roomUi.getRackTypeId());
		if (null == rackType) {
			throw new GenericInvalidDataException("Rack type not available, cannot add room");
		}
		PolicyTable policy = policyRepository.findOne(roomUi.getPolicyId());
		if (null == policy) {
			throw new GenericInvalidDataException("Policy not available, cannot add room");
		}

		if (roomUi.getNoOfRows() <= 0) {
			throw new GenericInvalidDataException("Can not create room with zero or negative values");
		}
		if (roomUi.getNoOfRacks() <= 0) {
			throw new GenericInvalidDataException("Can not create room with zero or negative values");
		}

		RoomTable room = new RoomTable();

		room.setName(getRoomName(projectTable));

		room.setProjectTable(projectTable);

		room.setRows(new ArrayList<RowTable>());
		room.setRackType(rackType);
		room.setPolicy(policy);
		RowTable row;
		localRoom.get().setAddRowStartTime(System.currentTimeMillis());
		for (int i = 0; i < roomUi.getNoOfRows(); i++) {
			row = new RowTable();
			int nextOffset = i + 1;
			row.setName(ROW_PREFIX + nextOffset);
			row.setRoom(room);
			row.setInheritedPolicy(policy);
			row.setRackType(rackType);
			// row.setInventoryInfo(new InventoryInfo());
			RackTable rack;
			row.setRacks(new ArrayList<RackTable>());
			for (int j = 0; j < roomUi.getNoOfRacks(); j++) {
				rack = new RackTable();
				nextOffset = j + 1;
				rack.setName(AciPhysicalSizerConstants.RACK_PREFIX + nextOffset);
				rack.setRackType(rackType);
				rack.setInheritedPolicy(policy);
				rack.setRow(row);
				InventoryInfo inv = initializeRackInventory(rackType);
				rack.setInventoryInfo((inv));
				row.getRacks().add(rack);
			}
			room.getRows().add(row);
		}
		room.setNoOfRacks(roomUi.getNoOfRacks());
		room.setNoOfRows(roomUi.getNoOfRows());
		localRoom.get().setAddRowStopTime(System.currentTimeMillis());

		localRoom.get().setCalculateInventoryStartTime(System.currentTimeMillis());
		roomHelper.calculateRoomAndRowInventoryInfo(room);
		localRoom.get().setCalculateInventoryStopTime(System.currentTimeMillis());
		
		localRoom.get().setRoomSaveStartTime(System.currentTimeMillis());
		roomRepository.save(room);
		localRoom.get().setRoomSaveEndTime(System.currentTimeMillis());
		
		LOGGER.info("addRoomStatistics : "+localRoom.get().toString());
		return room;
	}

	/**
	 * @param rackType
	 * @return
	 */
	private InventoryInfo initializeRackInventory(RackTypeTable rackType) {
		InventoryInfo inv = new InventoryInfo();
		inv.setTotalNumOfRus(rackType.getPhysicalStats().getNoOfRus());
		inv.setTotalNumOfRacks(1);
		inv.setTotalPower(rackType.getPowerCooling().getPower());
		inv.setTotalCooling(rackType.getPowerCooling().getCoolingInBTU());
		return inv;
	}

	public List<RoomTable> getRooms(int projId) {
		ProjectTable projectTable = projectsRepository.findOne(projId);
		return sortRooms(projectTable.getRooms());
	}

	@Transactional
	public RoomTable deleteRoom(int projId, int roomId) {
		RoomTable room = roomRepository.findOne(roomId);
		roomRepository.delete(roomId);
		List<RoomTable> rooms = roomRepository.findByProjectId(projId);
		sortRooms(rooms);

		int i = 1;
		for (RoomTable roomIt : rooms) {
			roomIt.setName(ROOM_PREFIX + i);
			i++;
		}

		roomRepository.save(rooms);

		return room;
	}

	/**
	 * @param rooms
	 * @return
	 */
	private List<RoomTable> sortRooms(List<RoomTable> rooms) {
		Collections.sort(rooms, new Comparator<RoomTable>() {

			@Override
			public int compare(RoomTable o1, RoomTable o2) {
				int room1Offset = getIntOffset(o1);
				int room2Offset = getIntOffset(o2);
				return room1Offset - room2Offset;
			}

			/**
			 * @param o1
			 * @return
			 */
			private int getIntOffset(RoomTable o1) {
				return Integer.parseInt(o1.getName().split("-")[1]);
			}
		});
		return rooms;
	}

	public RoomTable roomClone(int roomId) {
		RoomTable room = roomRepository.findOne(roomId);
		RoomTable newRoom = roomClone(room);
		return newRoom;
	}

	private RoomTable roomClone(RoomTable room) {
		RoomTable newRoom = new RoomTable();
		newRoom.setName(getRoomName(room.getProjectTable()));
		roomClone(room.getProjectTable(), room, newRoom);
		return newRoom;
	}

	private void roomClone(ProjectTable projectTable, RoomTable room, RoomTable newRoom) {
		newRoom.setInventoryInfo(room.getInventoryInfo());
		newRoom.setNoOfRacks(room.getNoOfRacks());
		newRoom.setNoOfRows(room.getNoOfRows());
		newRoom.setPolicy(room.getPolicy());
		newRoom.setProjectTable(projectTable);
		newRoom.setRackType(room.getRackType());
		newRoom.setRows(new ArrayList<RowTable>());
		rowServices.rowClone(room, newRoom);
		roomHelper.calculateRoomAndRowInventoryInfo(newRoom);
		roomRepository.saveAndFlush(newRoom);
	}

	public RoomTable projectRoomClone(ProjectTable projectTable, RoomTable room) {
		RoomTable newRoom = new RoomTable();
		newRoom.setName(room.getName());
		roomClone(projectTable, room, newRoom);
		return newRoom;
	}

	/**
	 * @param room
	 * @param projectTable
	 * @return
	 */
	private String getRoomName(ProjectTable projectTable) {
		if (projectTable.getRooms() != null) {
			int roomNo = projectTable.getRooms().size() + 1;
			String fullName = ROOM_PREFIX + roomNo;
			return fullName;
		} else {
			return ROOM_1;
		}
	}

	public RoomTable updateRoom(int projectId, int roomId, RoomUi roomUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException {
		ProjectTable projectTable = projectsRepository.findOne(projectId);
		if (null == projectTable) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}

		RoomTable room = roomRepository.findOne(roomId);
		if (null == room) {
			throw new AciEntityNotFound("Could not find the room");
		}

		if (roomUi.getNoOfRows() < room.getNoOfRows()) // do not allow
														// downgrading the row
														// count
		{
			throw new GenericInvalidDataException("Cannot decrease number of rows");
		}

		PolicyTable policy = policyRepository.findOne(roomUi.getPolicyId());

		if (null == policy) {
			throw new GenericInvalidDataException("Policy not available, cannot update room");
		}
		RowTable row = null;
		RackTypeTable rackType = room.getRackType();
		RackTypeTable requestedRackType = rackTypeRepository.findOne(roomUi.getRackTypeId());
		if (null == requestedRackType) {
			throw new GenericInvalidDataException("Rack type not available, cannot update row");
		}

		if (requestedRackType.getPhysicalStats().getNoOfRus() < rackType.getPhysicalStats().getNoOfRus()) {
			throw new GenericInvalidDataException("Cannot decrease rack units");
		}

		if (room.getNoOfRacks() > roomUi.getNoOfRacks()) {
			throw new GenericInvalidDataException("can not decrease racks");
		}

		updateRoomPolicy(roomUi, room, policy);

		for (int i = room.getNoOfRows(); i < roomUi.getNoOfRows(); i++) {
			row = new RowTable();
			int currentOffset = i + 1;
			row.setName(ROW_PREFIX + currentOffset);
			row.setRoom(room);
			row.setInheritedPolicy(policy);
			row.setInventoryInfo(new InventoryInfo());
			RackTable rack;
			row.setRacks(new ArrayList<RackTable>());
			for (int j = 0; j < roomUi.getNoOfRacks(); j++) {
				rack = new RackTable();
				currentOffset = j + 1;
				rack.setName(AciPhysicalSizerConstants.RACK_PREFIX + currentOffset);
				rack.setRackType(requestedRackType);
				rack.setInheritedPolicy(policy);
				rack.setRow(row);
				InventoryInfo inv = initializeRackInventory(rackType);
				rack.setInventoryInfo((inv));
				// rack.setInventoryInfo(new InventoryInfo());
				row.getRacks().add(rack);
			}
			room.getRows().add(row);
		}
		room.setNoOfRacks(roomUi.getNoOfRacks());
		room.setRackType(requestedRackType);
		room.setNoOfRows(roomUi.getNoOfRows());
		roomHelper.calculateRoomAndRowInventoryInfo(room);
		roomRepository.flush();

		return room;
	}

	private void updateRoomPolicy(RoomUi roomUi, RoomTable room, PolicyTable policy) {
		if (roomUi.getPolicyId() != room.getPolicy().getId()) {
			room.setPolicy(policy);
			for (RowTable row : room.getRows()) {
				if (row.isPolicyInherited())
					row.setPolicy(policy);
				for (RackTable rack : row.getRacks()) {
					if (rack.isPolicyInherited())
						rack.setPolicy(policy);
				}
			}
		}
	}

	public void calculateInventory(RoomTable roomTable) {
		roomHelper.calculateRoomRowAndRackInventoryInfo(roomTable);
	}

	public boolean terminateRoom(int projectId, int roomId, RoomTerminationUi roomUi)
			throws AciEntityNotFound, TerminationException {
		boolean success = true;

		int version = getMaxVersionForPlacement(roomId);
		if (roomUi.isTerminateRow()) {
			success = rowTermination(projectId, roomId, roomUi, version);
		}
		if (roomUi.isTerminateSpine()) {
			DeviceType deviceType = deviceTypeRepository.findOne(roomUi.getPreferredSpineId());
			RoomTable roomTable = roomRepository.findOne(roomId);
			spineTerminationAlgo.removeSpinesAndUpdatePortTerminationInfo(roomTable, deviceType);
			List<RackTable> networkRacks = getSpineRack(roomUi, roomId);

			// Return error if there are no racks to place to spine.
			// Or return error in one place after spine calcualtion. It is same
			// case as less number of rack space.
			//

			spineTerminationAlgo.spineTermination(roomTable, deviceType, networkRacks, version);
			calculateInventory(roomTable);
			roomRepository.saveAndFlush(roomTable);
		}
		return success;
	}

	private List<RackTable> getSpineRack(RoomTerminationUi roomUi, int roomId) throws TerminationException {
		if (!roomUi.isUseNetworkRack()) {
			RackTable rackTable = rackRepository.findOne(roomUi.getSpineRack().getRackId());
			validateRackForSpinePlacement(rackTable);
			rackTable.setNetworkTypeRack(true);
			ArrayList<RackTable> networkRacks = new ArrayList<RackTable>();
			networkRacks.add(rackTable);
			return networkRacks;
		} else {
			return rackRepository.findAllNetworkRacksByRoomId(roomId);
		}
	}

	private boolean rowTermination(int projectId, int roomId, RoomTerminationUi roomUi, int version)
			throws AciEntityNotFound, TerminationException {
		boolean success = true;
		for (Integer rowId : roomUi.getRowIds()) {
			List<UnterminatedRacks> unterminatedRacks = rowServices.terminateRow(projectId, roomId, rowId,
					roomUi.isAddLeaf(), roomUi.getSwitchUi(), version);
			if (!unterminatedRacks.isEmpty()) {
				success = false;
			}
		}
		return success;
	}

	private int getMaxVersionForPlacement(int roomId) {
		int version = 1;
		Integer maXVersion = switchRepository.getMaXVersionByRoom(roomId);
		if (maXVersion != null) {
			version = maXVersion + 1;
		}

		return version;
	}

	private void validateRackForSpinePlacement(RackTable rackTable) throws TerminationException {
		if (!rackTable.getServers().isEmpty()) {
			throw new TerminationException(AciPhysicalSizerConstants.SPINE_TERMINATION_FAILED);
		}
		if (!rackTable.getSwitches().isEmpty()) {
			for (SwitchTable switchIter : rackTable.getSwitches()) {
				if (!switchIter.getDeviceType().getType().equals(AciPhysicalSizerConstants.SPINE)) {
					throw new TerminationException(AciPhysicalSizerConstants.SPINE_TERMINATION_FAILED);
				}
			}
		}
	}

	public void revertRoom(int projectId, int roomId, RoomRevertUi roomRevertUi) throws AciEntityNotFound {
		switchRepository.deleteMaxVersionByRoom(roomId);
		for (Integer rowId : roomRevertUi.getRowIds()) {
			rowServices.deleteSwitchByRound(projectId, roomId, rowId);
		}

	}
}

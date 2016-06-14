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

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.PolicyTable;
import com.cisco.acisizer.domain.ProjectTable;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RackTypeTable;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.UnterminatedRacks;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.helper.RoomHelper;
import com.cisco.acisizer.helper.RowHelper;
import com.cisco.acisizer.physical.log.models.RowTerminationLog;
import com.cisco.acisizer.physical.rest.models.RowUi;
import com.cisco.acisizer.physical.rest.models.SwitchUi;
import com.cisco.acisizer.physical.sizing.algo.PlaceSwitchAuto;
import com.cisco.acisizer.physical.sizing.algo.TerminationAlgorithm;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.PolicyRepository;
import com.cisco.acisizer.repo.ProjectsRepository;
import com.cisco.acisizer.repo.RackTypeRepository;
import com.cisco.acisizer.repo.RoomRepository;
import com.cisco.acisizer.repo.RowRepository;
import com.cisco.acisizer.repo.SwitchRepository;
import com.cisco.acisizer.util.ACISizerConstant;

/**
 * @author Mahesh
 *
 */
@Service
public class RowServices {

	public static final String ENTITY_NAME_SEPERATOR = "-";

	public static final String ROW_PREFIX = "Row-";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RowServices.class);

	@Inject
	private ProjectsRepository projectsRepository;

	@Inject
	private RoomRepository roomRepository;

	@Inject
	private RowRepository rowRepository;

	@Inject
	private PolicyRepository policyRepository;

	@Inject
	private RackTypeRepository rackTypeRepository;

	@Inject
	private SwitchServices switchServices;

	@Inject
	private RoomHelper roomHelper;

	@Inject
	private RowHelper rowHelper;

	@Inject
	private TerminationAlgorithm terminationAlgorithm;

	@Inject
	private PlaceSwitchAuto placeSwitchAuto;

	@Inject
	private SwitchRepository switchRepository;

	@Inject
	private RackServices rackServices;

	public List<RowTable> getRows(int projId, int roomId) {
		return sortRows(roomRepository.findOne(roomId).getRows());
	}

	public RowTable deleteRow(int projectId, int roomId, int rowId) {
		RowTable rowTable = rowRepository.getOne(rowId);
		rowRepository.delete(rowId);
		sortRows(rowTable.getRoom().getRows());

		updateNames(rowTable);

		// finally calculate the inventory
		RoomTable room = roomRepository.findOne(roomId);
		room.setNoOfRows(rowTable.getRoom().getNoOfRows() - 1);
		roomHelper.calculateInventryInfo(room);
		roomRepository.save(room);

		return rowTable;
	}

	private void updateNames(RowTable rowTable) {
		int i = 1;
		for (RowTable rowIt : rowTable.getRoom().getRows()) {
			rowIt.setName(ROW_PREFIX + i);
			i++;
		}

	}

	/**
	 * @param rooms
	 * @return
	 */
	private List<RowTable> sortRows(List<RowTable> rows) {
		Collections.sort(rows, new Comparator<RowTable>() {

			@Override
			public int compare(RowTable o1, RowTable o2) {
				int row1Offset = getIntOffset(o1);
				int row2Offset = getIntOffset(o2);
				return row1Offset - row2Offset;
			}

			/**
			 * @param o1
			 * @return
			 */
			private int getIntOffset(RowTable o1) {
				return Integer.parseInt(o1.getName().split(ENTITY_NAME_SEPERATOR)[1]);
			}
		});
		return rows;
	}

	public RowTable updateRow(int projectId, int roomId, int rowId, RowUi rowUi)
			throws AciEntityNotFound, GenericInvalidDataException, GenericCouldNotSaveException {

		ProjectTable projectTable = projectsRepository.findOne(projectId);
		if (null == projectTable) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}

		RoomTable room = roomRepository.findOne(roomId);
		if (null == room) {
			throw new AciEntityNotFound("Could not find the room");
		}

		RowTable rowTable = rowRepository.findOne(rowId);
		if (null == rowTable) {
			throw new AciEntityNotFound("Could not find the row");
		}

		if (rowUi.getNoOfRacks() < rowTable.getRacks().size()) {
			throw new GenericInvalidDataException("Cannot decrease number of racks");
		}

		RackTypeTable rackType = rackTypeRepository.findOne(rowUi.getRackTypeId());

		if (null == rackType) {
			throw new GenericInvalidDataException("Rack type not available, cannot update row");
		}

		PolicyTable policy = null;

		if (rowUi.isPolicyInherited()) {
			policy = room.getPolicy();
		} else {
			policy = policyRepository.findOne(rowUi.getPolicyId());
		}

		if (null == policy) {
			throw new GenericInvalidDataException("Policy not available, cannot update row");
		}

		// store rack type in the row

		RackTable rack = null;
		int currentOffset = 0;

		updateRowPolicy(rowUi, rowTable, policy);

		for (int i = rowTable.getRacks().size(); i < rowUi.getNoOfRacks(); i++) {
			rack = new RackTable();
			currentOffset = i + 1;
			rack.setName(AciPhysicalSizerConstants.RACK_PREFIX + currentOffset);
			rack.setRackType(rackType);
			rack.setInheritedPolicy(policy);
			rack.setRow(rowTable);
			rack.setInventoryInfo(initializeRackInventory(rackType));
			rowTable.getRacks().add(rack);
		}
		rowTable.setRackType(rackType);
		rowRepository.flush();
		roomHelper.calculateRoomAndRowInventoryInfo(room);
		roomRepository.save(room);

		return rowTable;
	}

	public List<UnterminatedRacks> terminateRow(int projectId, int roomId, int rowId, boolean isManual,
			SwitchUi switchUi) throws AciEntityNotFound, TerminationException {
		int version = getMaxVersionOfPlacement(rowId);
		return terminateRow(projectId, roomId, rowId, isManual, switchUi, version);
	}

	public List<UnterminatedRacks> terminateRow(int projectId, int roomId, int rowId, boolean isManual,
			SwitchUi switchUi, int version) throws AciEntityNotFound, TerminationException {
		if (isManual && switchUi.getPlacementPattern() != null && switchUi.getTemplateTypeId() > 0) {
			return terminateRowAuto(projectId, roomId, rowId, switchUi, version);
		} else {
			return terminateRowManual(projectId, roomId, rowId, switchUi);
		}
	}

	private List<UnterminatedRacks> terminateRowManual(int projectId, int roomId, int rowId, SwitchUi switchUi)
			throws AciEntityNotFound {
		localRow.get().setValidationStartTime(System.currentTimeMillis());
		RowTable rowTable = validateIds(projectId, roomId, rowId);
		localRow.get().setValidationEndTime(System.currentTimeMillis());

		localRow.get().setTerminationAlgorithmStartTime(System.currentTimeMillis());
		List<UnterminatedRacks> unterminatedRacks = terminationAlgorithm.terminate(rowTable);
		localRow.get().setTerminationAlgorithmStopTime(System.currentTimeMillis());

		calculateInventoryAndSave(rowTable);
		return unterminatedRacks;
	}

	public List<UnterminatedRacks> terminateRowAuto(int projectId, int roomId, int rowId, SwitchUi switchUi,
			int version) throws AciEntityNotFound, TerminationException {
		localRow.get().setValidationStartTime(System.currentTimeMillis());
		RowTable rowTable = validateIds(projectId, roomId, rowId);
		localRow.get().setValidationEndTime(System.currentTimeMillis());

		localRow.get().setAutoPlaceSwitchesStartTime(System.currentTimeMillis());
		placeSwitchAuto.PlaceSwitches(rowTable, switchUi, version);
		localRow.get().setAutoPlaceSwitchesEndTime(System.currentTimeMillis());

		localRow.get().setTerminationAlgorithmStartTime(System.currentTimeMillis());
		List<UnterminatedRacks> unterminatedRacks = terminationAlgorithm.terminate(rowTable);
		localRow.get().setTerminationAlgorithmStopTime(System.currentTimeMillis());

		localRow.get().setRemoveUnUsedSwitchesStartTime(System.currentTimeMillis());
		placeSwitchAuto.removeUnusedSwitches(rowTable, version);
		localRow.get().setRemoveUnUsedSwitchesStopTime(System.currentTimeMillis());

		// We can improve the performance, if we call inventory update only if
		// switches are removed.
		localRow.get().setCalculateInventoryStartTime(System.currentTimeMillis());
		calculateInventoryAndSave(rowTable);
		localRow.get().setCalculateInventoryStopTime(System.currentTimeMillis());
		
		LOGGER.info("rowTerminationTimeStatistics : "+localRow.get().toString());
		return unterminatedRacks;
	}

	private void calculateInventoryAndSave(RowTable rowTable) {
		
		calculateInventory(rowTable);
		
		rowRepository.saveAndFlush(rowTable);
	}

	@Transactional
	public void deleteSwitchByRound(int projectId, int roomId, int rowId) throws AciEntityNotFound {
		RowTable rowTable = validateIds(projectId, roomId, rowId);
		switchServices.deleteSwitchByRound(rowId);
		rowTable.setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		calculateInventoryAndSave(rowTable);
	}

	private void calculateInventory(RowTable rowTable) {
		rowHelper.calculateRowAndRackInventoryInfo(rowTable);
		roomHelper.calculateInventryInfo(rowTable.getRoom());
	}

	private RowTable validateIds(int projectId, int roomId, int rowId) throws AciEntityNotFound {

		RowTable rowTable = rowRepository.findOne(rowId);

		if (null == rowTable) {
			throw new AciEntityNotFound("Could not find the row");
		}

		if (rowTable.getRoom().getId() != roomId) {
			throw new AciEntityNotFound("Could not find the room");
		}

		if (rowTable.getRoom().getProjectTable().getId() != projectId) {
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}

		return rowTable;

	}

	private InventoryInfo initializeRackInventory(RackTypeTable rackType) {
		InventoryInfo inv = new InventoryInfo();
		inv.setTotalNumOfRus(rackType.getPhysicalStats().getNoOfRus());
		inv.setTotalNumOfRacks(1);
		inv.setTotalPower(rackType.getPowerCooling().getPower());
		inv.setTotalCooling(rackType.getPowerCooling().getCoolingInBTU());
		return inv;
	}

	public static final ThreadLocal<RowTerminationLog> localRow = new ThreadLocal<RowTerminationLog>() {
		@Override
		protected RowTerminationLog initialValue() {
			return new RowTerminationLog();
		}
	};

	private void updateRowPolicy(RowUi rowUi, RowTable rowTable, PolicyTable policy) {

		if (rowUi.getPolicyId() != rowTable.getPolicy().getId()) {
			rowTable.setPolicy(policy);
			if (rowUi.isPolicyInherited()) {
				for (RackTable rack : rowTable.getRacks()) {
					rack.setPolicy(policy);
					rack.setPolicyInherited(true);
				}
			}
		}
		rowTable.setPolicyInherited(rowUi.isPolicyInherited());
	}

	private int getMaxVersionOfPlacement(int rowId) {
		int versionId = 1;
		Integer maXVersion = switchRepository.getMaXVersionByRow(rowId);
		if (maXVersion != null) {
			versionId = maXVersion + 1;
		}

		return versionId;
	}

	public void rowClone(RoomTable room, RoomTable newRoom) {
		RowTable rowTemp;
		for (RowTable row : room.getRows()) {
			rowTemp = new RowTable();
			rowTemp.setRacks(new ArrayList<RackTable>());
			// row.clone(rowTemp);
			rowTemp.setInventoryInfo(row.getInventoryInfo());
			rowTemp.setName(row.getName());
			rowTemp.setPolicy(row.getPolicy());
			rowTemp.setRackType(row.getRackType());
			rowTemp.setRoom(newRoom);
			rowTemp.setState(row.getState());
			rowTemp.setTotalBandwidth(rowTemp.getTotalBandwidth());
			rowTemp.setUnterminatedRacks(row.getUnterminatedRacks());
			// rowTemp.setInheritedPolicy(row.isPolicyInherited());
			rowTemp.setPolicyInherited(row.isPolicyInherited());

			rackServices.rackClone(rowTemp, row);
			rowTemp.setRoom(newRoom);
			newRoom.getRows().add(rowTemp);
		}
	}
}

package com.cisco.acisizer.physical.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.PolicyTable;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RackTypeTable;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.ServerTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.helper.DeviceHelper;
import com.cisco.acisizer.helper.RoomHelper;
import com.cisco.acisizer.helper.RowHelper;
import com.cisco.acisizer.physical.rest.models.RackUi;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.PolicyRepository;
import com.cisco.acisizer.repo.RackRepository;
import com.cisco.acisizer.repo.RackTypeRepository;
import com.cisco.acisizer.repo.RoomRepository;
import com.cisco.acisizer.repo.RowRepository;

@Service
public class RackServices {
	@Inject
	private RackRepository rackRepository;

	@Inject
	private RowRepository rowRepository;

	@Inject
	private RoomRepository roomRepository;

	@Inject
	private RowHelper rowHelper;

	@Inject
	private RoomHelper roomHelper;

	@Inject
	private PolicyRepository policyRepository;

	@Inject
	private RackTypeRepository rackTypeRepository;

	@Inject
	private DeviceHelper deviceHelper;

	public RackTable deleteRack(int projectId, int roomId, int rowId, int rackId) {
		RackTable ret = rackRepository.getOne(rackId);
		rackRepository.delete(rackId);
		sortRacks(ret.getRow().getRacks());
		updateNames(ret);

		RowTable row = rowRepository.findOne(rowId);
		rowHelper.calculateInventryInfo(row);
		rowRepository.save(row);

		RoomTable room = roomRepository.findOne(roomId);

		roomHelper.calculateInventryInfo(room);
		roomRepository.save(room);

		return ret;
	}

	private void updateNames(RackTable ret) {
		int i = 1;
		for (RackTable rackIt : ret.getRow().getRacks()) {
			rackIt.setName((AciPhysicalSizerConstants.RACK_PREFIX + i));
			i++;
		}

	}

	public List<RackTable> getAllRacks(int projectId, int roomId, int rowId) {
		List<RackTable> result = rowRepository.findOne(rowId).getRacks();
		sortRacks(result);
		return result;
	}

	private void sortRacks(List<RackTable> racks) {
		Collections.sort(racks, new Comparator<RackTable>() {

			@Override
			public int compare(RackTable o1, RackTable o2) {
				int rack1Offset = getIntOffset(o1);
				int rack2Offset = getIntOffset(o2);
				return rack1Offset - rack2Offset;
			}

			/**
			 * @param o1
			 * @return
			 */
			private int getIntOffset(RackTable o1) {
				return Integer.parseInt(o1.getName().split("-")[1]);
			}

		});

	}

	public RackTable updateRack(int projectId, int roomId, int rowId, int rackId, RackUi rackUi)
			throws AciEntityNotFound, GenericInvalidDataException {
		// TODO Auto-generated method stub

		RackTable rackTable = rackRepository.findOne(rackId);

		if (null == rackTable) {
			throw new AciEntityNotFound("Could not find the rack");
		}

		if (rackTable.getRow().getId() != rowId) {
			throw new AciEntityNotFound("Could not find the row");
		}

		if (rackTable.getRow().getRoom().getId() != roomId) {
			throw new AciEntityNotFound("Could not find the room");
		}
		if (rackTable.getRow().getRoom().getProjectTable().getId() != projectId) {
			throw new AciEntityNotFound("Could not find the project");
		}

		RackTypeTable rackType = rackTypeRepository.findOne(rackUi.getRackTypeId());
		if (null == rackType) {
			throw new AciEntityNotFound("Could not find the rack type");
		}

		if (rackTable.getRackType().getPhysicalStats().getNoOfRus() > rackType.getPhysicalStats().getNoOfRus()) {
			throw new GenericInvalidDataException("Could not decrease the rack type");
		}

		PolicyTable policy = null;

		if (rackUi.isPolicyInherited()) {
			policy = rackTable.getRow().getPolicy();
		} else {
			policy = policyRepository.findOne(rackUi.getPolicyId());
		}

		if (null == policy) {
			throw new GenericInvalidDataException("Policy not available, cannot update rack");
		}

		validateNetworkRackTypeUpdate(rackTable, rackUi);
		rackTable.setNetworkTypeRack(rackUi.getisNetworkTypeRack());
		rackTable.setRackType(rackType);
		rackTable.setPolicy(policy);
		rackTable.setPolicyInherited(rackUi.isPolicyInherited());
		deviceHelper.calculateInventryInfo(rackTable);
		rackRepository.save(rackTable);

		return rackTable;
	}

	private void validateNetworkRackTypeUpdate(RackTable rackTable, RackUi rackUi) throws GenericInvalidDataException {
		if (rackUi.getisNetworkTypeRack() == true) {
			if (!(rackTable.getServers().size() == 0)) {
				throw new GenericInvalidDataException("Can’t change to network rack, servers exist");
			}
			for (SwitchTable itr : rackTable.getSwitches()) {
				if (!(itr.getDeviceType().getType().equals(AciPhysicalSizerConstants.SPINE))) {

					throw new GenericInvalidDataException(
							"changing to network rack failed, remove non spine devices and try again");
				}

			}

		}

	}

	public void rackClone(RowTable rowTemp, RowTable row) {
		RackTable rackTemp;
		for (RackTable rack : row.getRacks()) {

			rackTemp = new RackTable();
			rackTemp.setAggPortDomainListD(rack.getAggPortDomainListD());
			rackTemp.setAggPortDomainListD1(rack.getAggPortDomainListD1());
			rackTemp.setAggPortDomainListD2(rack.getAggPortDomainListD2());
			rackTemp.setEndOfRow(rack.isEndOfRow());
			rackTemp.setPolicyInherited(rack.isPolicyInherited());
			rackTemp.setName(rack.getName());
			rackTemp.setNetworkTypeRack(rack.isNetworkTypeRack());
			rackTemp.setPolicy(rack.getPolicy());
			rackTemp.setTotalBandwidth(rack.getTotalBandwidth());
			rackTemp.setTerminated(rack.isTerminated());
			rackTemp.setInventoryInfo(rack.getInventoryInfo());
			rackTemp.setRackType(rack.getRackType());
			rackTemp.setRow(rowTemp);

			rackTemp.setServers(new ArrayList<ServerTable>());
			serverDeviceClone(rackTemp, rack);

			rackTemp.setSwitches(new ArrayList<SwitchTable>());
			switchDeviceClone(rackTemp, rack);

			rowTemp.getRacks().add(rackTemp);
		}
	}

	private void serverDeviceClone(RackTable rackTemp, RackTable rack) {
		ServerTable serverTemp = null;
		for (ServerTable serverItr : rack.getServers()) {
			serverTemp = new ServerTable();
			serverTemp.setDeviceType(serverItr.getDeviceType());
			serverTemp.setNumOfInstances(serverItr.getNumOfInstances());
			serverTemp.setPortDomainD(serverItr.getPortDomainD());
			serverTemp.setPortDomainD1(serverItr.getPortDomainD1());
			serverTemp.setPortDomainD2(serverItr.getPortDomainD2());
			serverTemp.setPortGroups(serverItr.getPortGroups());
			serverTemp.setRackTable(rackTemp);
			serverTemp.setUcsManaged(serverItr.isUcsManaged());
			rackTemp.getServers().add(serverTemp);
		}
	}

	private void switchDeviceClone(RackTable rackTemp, RackTable rack) {
		SwitchTable switchTemp;
		for (SwitchTable switchItr : rack.getSwitches()) {
			switchTemp = new SwitchTable();
			switchTemp.setDeviceType(switchItr.getDeviceType());
			switchTemp.setPortDomainD(switchItr.getPortDomainD());
			switchTemp.setRack(rackTemp);
			switchTemp.setNumOfLinks(switchItr.getNumOfLinks());
			switchTemp.setPlacedIn(switchItr.getPlacedIn());
			switchTemp.setPortGroups(switchItr.getPortGroups());
			switchTemp.setRackTable(rackTemp);
			switchTemp.setSpineTerminationDone(switchItr.isSpineTerminationDone());
			switchTemp.setUplinkBandwidth(switchItr.getUplinkBandwidth());
			rackTemp.getSwitches().add(switchTemp);

		}
	}
}

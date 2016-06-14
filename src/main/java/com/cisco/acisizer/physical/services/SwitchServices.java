/**
 * 
 */
package com.cisco.acisizer.physical.services;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.PortGroup;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RackTorMappingTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.helper.DeviceHelper;
import com.cisco.acisizer.physical.rest.models.SwitchUi;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.DeviceTypeRepository;
import com.cisco.acisizer.repo.RackRepository;
import com.cisco.acisizer.repo.RackTorMappingRepository;
import com.cisco.acisizer.repo.SwitchRepository;
import com.cisco.acisizer.util.ACISizerConstant;

/**
 * @author Mahesh
 *
 */
@Service
public class SwitchServices {

	@Inject
	private SwitchRepository switchRepository;

	@Inject
	private RackTorMappingRepository rackTorMappingRepository;

	@Inject
	private RackRepository rackRepository;

	@Inject
	private DeviceTypeRepository deviceTypeRepository;

	@Inject
	private DeviceHelper deviceHelper;

	public SwitchTable addSwitch(int projectId, int roomId, int rowId, int rackId, SwitchUi switchUi)
			throws AciEntityNotFound, GenericCouldNotSaveException, GenericInvalidDataException {

		RackTable rackTable = validateIds(projectId, roomId, rowId, rackId);
		SwitchTable switchTemp = initializeSwitchWithTemplateType(switchUi.getTemplateTypeId(), rackTable);

		if (rackTable.isNetworkTypeRack() == true) {
			if (switchTemp.getDeviceType().getType().equals(AciPhysicalSizerConstants.LEAF)) {
				throw new GenericInvalidDataException("can't add leaf to network rack");
			}
		}
		switchTemp.setPortGroups(switchUi.getPortGroups());
		rackTable.getRow().setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		addSwitchAndUpdateInventory(rackTable, switchTemp);

		return switchTemp;
	}

	public void addSwitchAuto(RackTable rackTable, SwitchUi switchUi, int versionId) throws TerminationException {
		DeviceType deviceType = deviceTypeRepository.findOne(switchUi.getTemplateTypeId());
		if (rackTable.isNetworkTypeRack() == false) {
			if (deviceType != null) {
				addSwitchAuto(rackTable, versionId, deviceType);
			}

		}
	}

	public boolean addSwitchAuto(RackTable rackTable, int versionId, DeviceType deviceType)
			throws TerminationException {
		InventoryInfo inv = rackTable.getInventoryInfo();
		if (isRackUnitsAvailable(deviceType, inv)) {
			SwitchTable switchTemp = new SwitchTable();
			switchTemp.setRackTable(rackTable);
			switchTemp.setDeviceType(deviceType);
			switchTemp.setPortGroups(switchTemp.getDeviceType().getDefaultPortGroup());
			switchTemp.setPlacedIn(versionId);
			addSwitchAndUpdateInventory(rackTable, switchTemp);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param rackTable
	 * @param switchTemp
	 */
	private void addSwitchAndUpdateInventory(RackTable rackTable, SwitchTable switchTemp) {
		calculatePortDomains(switchTemp);
		rackTable.getSwitches().add(switchTemp);
		deviceHelper.calculateInventryInfo(rackTable);

		rackRepository.flush();
	}

	/**
	 * @param templateId
	 * @param rackTable
	 * @return
	 * @throws AciEntityNotFound
	 */
	private SwitchTable initializeSwitchWithTemplateType(int templateId, RackTable rackTable) throws AciEntityNotFound {
		DeviceType deviceType = deviceTypeRepository.findOne(templateId);
		if (null == deviceType) {
			throw new AciEntityNotFound("Could not find the device type");
		}

		InventoryInfo inv = rackTable.getInventoryInfo();
		checkRackUnitsAvailability(deviceType, inv);
		SwitchTable switchTemp = new SwitchTable();
		switchTemp.setRackTable(rackTable);
		switchTemp.setDeviceType(deviceType);
		return switchTemp;
	}

	private void checkRackUnitsAvailability(DeviceType deviceType, InventoryInfo inv) throws AciEntityNotFound {
		int availableRUs = inv.getTotalNumOfRus() - inv.getRusConsumed();
		int requiredRus = deviceType.getPhysicalStats().getNoOfRus();
		if (requiredRus > availableRUs) {

			throw new AciEntityNotFound("No sufficient rack units available to place the device" + " available RUs :"
					+ availableRUs + ", required RUs:" + requiredRus);
		}

	}

	private boolean isRackUnitsAvailable(DeviceType deviceType, InventoryInfo inv) {
		int availableRUs = inv.getTotalNumOfRus() - inv.getRusConsumed();
		int requiredRus = deviceType.getPhysicalStats().getNoOfRus();
		return requiredRus <= availableRUs;
	}

	public SwitchTable updateSwitch(int projectId, int roomId, int rowId, int rackId, int switchId, SwitchUi switchUi)
			throws AciEntityNotFound, GenericCouldNotSaveException {
		validateIds(projectId, roomId, rowId, rackId);

		SwitchTable switchTemp = switchRepository.findOne(switchId);
		switchTemp.setPortGroups(switchUi.getPortGroups());

		calculatePortDomains(switchTemp);
		deviceHelper.calculateInventryInfo(switchTemp.getRack());
		switchTemp.getRack().getRow().setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		if (null == switchRepository.save(switchTemp)) {
			throw new GenericCouldNotSaveException("Could not save switch data");
		}

		return switchTemp;

	}

	@Transactional
	public SwitchTable deleteSwitch(int projectId, int roomId, int rowId, int rackId, int switchId)
			throws AciEntityNotFound {
		validateIds(projectId, roomId, rowId, rackId);
		RackTable rackTable = rackRepository.findOne(rackId);

		SwitchTable switchTemp = switchRepository.findOne(switchId);
		

		
		updateInventoryOnDeletion(switchTemp);
		//switchRepository.delete(switchId);
		rackTable.getSwitches().remove(switchTemp);
		deviceHelper.calculateInventryInfo(switchTemp.getRack());
		rackTable.getRow().setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		rackRepository.save(rackTable);
		return switchTemp;

	}

	private void updateInventoryOnDeletion(SwitchTable switchTemp) {
		List<RackTorMappingTable> rackTorMappings =  rackTorMappingRepository.findRackTorMappingByswitchId(switchTemp);
		for (RackTorMappingTable rackTorMappingTable : rackTorMappings) {
			updateRackPortInventory(rackTorMappingTable.getRackTable(), rackTorMappingTable.getDomain(),
					rackTorMappingTable.getSpeed(), rackTorMappingTable.getNumOfPortsTerminated());
			deviceHelper.calculateInventryInfo(rackTorMappingTable.getRackTable());
		}
		rackRepository.flush();
		rackTorMappingRepository.deleteBySwitchID(switchTemp);
	}

	private void updateRackPortInventory(RackTable rackTable, String domain, String speed, int numOfPorts) {
		PortDomain portDomain = getPortDomainOfRack(rackTable, domain, speed);
		portDomain.setNumOfPortsTerminated(portDomain.getNumOfPortsTerminated() - numOfPorts);
	}

	private PortDomain getPortDomainOfRack(RackTable rackTable, String domain, String speed) {
		PortDomain portDomain = null;
		switch (domain) {
		case AciPhysicalSizerConstants.PORT_DOMAIN_D:
			portDomain = rackTable.getAggPortDomainListD().get(speed);

			break;
		case AciPhysicalSizerConstants.PORT_DOMAIN_D1:
			portDomain = rackTable.getAggPortDomainListD1().get(speed);

			break;
		case AciPhysicalSizerConstants.PORT_DOMAIN_D2:
			portDomain = rackTable.getAggPortDomainListD2().get(speed);

			break;

		}
		return portDomain;
	}

	public void deleteSwitchByRound(int rowId) {
		switchRepository.deleteMaxVersionByRow(rowId);
	}

	public List<SwitchTable> getSwitches(int projectId, int roomId, int rowId, int rackId) throws AciEntityNotFound {
		validateIds(projectId, roomId, rowId, rackId);

		return rackRepository.findOne(rackId).getSwitches();
	}

	private RackTable validateIds(int projectId, int roomId, int rowId, int rackId) throws AciEntityNotFound {

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
			throw new AciEntityNotFound(ACISizerConstant.COULD_NOT_FIND_THE_PROJECT_FOR_ID);
		}
		return rackTable;

	}

	private void calculatePortDomains(SwitchTable src) {

		HashMap<String, PortDomain> portDomainD = new HashMap<String, PortDomain>();
		for (PortGroup iter : src.getPortGroups()) {
			deviceHelper.addPortDomain(portDomainD, iter, iter.getNumOfPorts(),
					AciPhysicalSizerConstants.PORT_DOMAIN_D);
		}
		src.setPortDomainD(portDomainD);
	}
}

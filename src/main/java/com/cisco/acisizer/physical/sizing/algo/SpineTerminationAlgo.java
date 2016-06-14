/**
 * 
 */
package com.cisco.acisizer.physical.sizing.algo;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.PortGroup;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.exceptions.TerminationException;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.SwitchRepository;

/**
 * @author Mahesh
 *
 */
@Component
public class SpineTerminationAlgo {

	public static final int INITIAL_VALUE = 0;

	public static final int MIN_NUMBER_OF_SPINES = 2;

	@Inject
	private PlaceSwitchAuto placeSwitchAuto;

	@Inject
	private SwitchRepository switchRepository;

	private void calculateSummationOfDownlinkBandwidthRackUsingTotalPorts(RackTable rackTable, double overSubscription,
			DeviceType spine) {
		for (SwitchTable switchTable : rackTable.getSwitches()) {
			calculateDownLinkBandwidthRackByPortDomain(rackTable, switchTable, overSubscription, spine);
		}
	}

	private void calculateDownLinkBandwidthRackByPortDomain(RackTable rack, SwitchTable switchTable,
			double overSubscription, DeviceType spine) {
		Map<String, PortDomain> portDomainDSwitch = switchTable.getPortDomainD();
		for (String speed : portDomainDSwitch.keySet()) {
			PortDomain portDomain = portDomainDSwitch.get(speed);
			int uplinkBandwidth = (int) Math
					.ceil((double) portDomain.getNumOfPorts() * portDomain.getSpeed() / overSubscription);

			switchTable.setUplinkBandwidth(switchTable.getUplinkBandwidth() + uplinkBandwidth);
			rack.setTotalBandwidth(rack.getTotalBandwidth() + uplinkBandwidth);
		}
		int numOfLinksToOneSpine = (int) Math
				.ceil((double) switchTable.getUplinkBandwidth() / getMaxBandwidthSwitch(spine));
		switchTable.setNumOfLinks(numOfLinksToOneSpine);
	}

	private void calculateSummationOfDownlinkBandwidthRow(RowTable rowTable, double overSubscription,
			DeviceType spine) {
		for (RackTable rack : rowTable.getRacks()) {
			calculateSummationOfDownlinkBandwidthRackUsingTotalPorts(rack, overSubscription, spine);
			rowTable.setTotalBandwidth(rowTable.getTotalBandwidth() + rack.getTotalBandwidth());
		}
	}

	private void calculateSummationOfDownlinkBandwidthRoom(RoomTable roomTable, DeviceType spine) {
		for (RowTable row : roomTable.getRows()) {
			calculateSummationOfDownlinkBandwidthRow(row, roomTable.getPolicy().getDetails().getLeafOversubscription(),
					spine);
			roomTable.setTotalBandwidth(roomTable.getTotalBandwidth() + row.getTotalBandwidth());

		}
	}

	private int getTotalPortsOfSpine(DeviceType spine) {
		int totalPorts = 0;
		for (PortGroup portGroup : spine.getDefaultPortGroup()) {
			totalPorts = totalPorts + portGroup.getNumOfPorts();
		}
		return totalPorts;
	}

	public void spineTermination(RoomTable roomTable, DeviceType spine, List<RackTable> networkRacks, int version)
			throws TerminationException {
		int numOfSpinesRequired = MIN_NUMBER_OF_SPINES;
		int numOfSpines = calculateSpineRequirement(roomTable, spine);
		if (numOfSpines > MIN_NUMBER_OF_SPINES) {
			numOfSpinesRequired = numOfSpines;
		}
		placeSpines(networkRacks, spine, numOfSpinesRequired, version);
		terminateSpine(roomTable);
	}

	public void removeSpinesAndUpdatePortTerminationInfo(RoomTable roomTable, DeviceType spine) {
		int numOfSpines = switchRepository.getSpineCountByRoom(roomTable.getId());
		switchRepository.deleteSpineByRoom(roomTable.getId());
		List<SwitchTable> tors = switchRepository.getAllTorsOfRoom(roomTable.getId());
		for (SwitchTable switchTable : tors) {
			int numOfLinks = switchTable.getNumOfLinks();
			int numOfportsToFree = numOfLinks * numOfSpines;
			PortDomain portDomain = switchTable.getPortDomainD().get(getMaxBandwidthWithUnit(spine));
			portDomain.setNumOfPortsTerminated(portDomain.getNumOfPortsTerminated() - numOfportsToFree);
			switchTable.setNumOfLinks(INITIAL_VALUE);
			switchTable.setUplinkBandwidth(INITIAL_VALUE);
			switchRepository.saveAndFlush(switchTable);
		}

	}

	private String getMaxBandwidthWithUnit(DeviceType spine) {
		return "" + getMaxBandwidthSwitch(spine) + AciPhysicalSizerConstants.SPEED_UNIT_G;
	}

	private void placeSpines(List<RackTable> networkRacks, DeviceType spine, int numOfSpines, int version)
			throws TerminationException {
		placeSwitchAuto.placeSpine(networkRacks, spine, numOfSpines, version);

	}

	private int calculateSpineRequirement(RoomTable roomTable, DeviceType spine) {
		calculateSummationOfDownlinkBandwidthRoom(roomTable, spine);
		return (int) Math.ceil(
				(double) roomTable.getTotalBandwidth() / (getTotalPortsOfSpine(spine) * getMaxBandwidthSwitch(spine)));
	}

	private int getMaxBandwidthSwitch(DeviceType spine) {
		int max = 0;
		for (PortGroup portGroup : spine.getDefaultPortGroup()) {
			if (portGroup.getSpeedInt() > max) {
				max = portGroup.getSpeedInt();
			}
		}
		return max;
	}

	private void terminateSpine(RoomTable roomTable) throws TerminationException {
		List<SwitchTable> tors = switchRepository.getAllTorsOfRoom(roomTable.getId());
		List<SwitchTable> spines = switchRepository.getAllSpinesOfRoom(roomTable.getId());
		boolean spineTerminationFlag = true;

		for (SwitchTable spine : spines) {
			for (SwitchTable tor : tors) {
				spineTerminationFlag = !tor.isSpineTerminationDone();
				PortDomain portDomainSpine = spine.getPortDomainD().get(getMaxBandwidthWithUnit(spine.getDeviceType()));
				if (portDomainSpine.getPortsToTerminate() < tor.getNumOfLinks()) {
					throw new TerminationException(AciPhysicalSizerConstants.SPINE_TERMINATION_FAILED);
				} else {
					PortDomain portDomainTor = tor.getPortDomainD().get(getMaxBandwidthWithUnit(spine.getDeviceType()));
					if (portDomainTor.getPortsToTerminate() < tor.getNumOfLinks()) {
						throw new TerminationException(AciPhysicalSizerConstants.SPINE_TERMINATION_FAILED);
					} else {
						portDomainTor
								.setNumOfPortsTerminated(portDomainTor.getNumOfPortsTerminated() + tor.getNumOfLinks());
						portDomainSpine.setNumOfPortsTerminated(
								portDomainSpine.getNumOfPortsTerminated() + tor.getNumOfLinks());
					}
				}
				tor.setSpineTerminationDone(spineTerminationFlag);
				switchRepository.save(tor);
			}
			spine.setSpineTerminationDone(true);
			switchRepository.save(spine);

		}

	}

}

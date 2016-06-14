/**
 * 
 */
package com.cisco.acisizer.physical.sizing.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.PortGroup;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RackTorMappingTable;
import com.cisco.acisizer.domain.RowTable;
import com.cisco.acisizer.domain.ServerTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.domain.UnterminatedRacks;
import com.cisco.acisizer.helper.DeviceHelper;
import com.cisco.acisizer.physical.services.RowServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.RackRepository;
import com.cisco.acisizer.repo.RackTorMappingRepository;
import com.cisco.acisizer.repo.RowRepository;
import com.cisco.acisizer.repo.SwitchRepository;

/**
 * @author Mahesh
 *
 */
@Component
public class TerminationAlgorithm {

	@Inject
	private RackTorMappingRepository rackTorMappingRepository;

	@Inject
	private RackRepository rackRepository;

	@Inject
	private DeviceHelper deviceHelper;

	@Inject
	private RowRepository rowRepository;

	@Inject
	private SwitchRepository switchRepository;

	public List<UnterminatedRacks> terminate(RowTable row) {
		clearTerminatedStats(row);
		localTermination(row);
		List<UnterminatedRacks> unterminatedRacks = neighbourTermination(row);
		row.setUnterminatedRacks(unterminatedRacks);
		if (!unterminatedRacks.isEmpty()) {
			row.setState(AciPhysicalSizerConstants.TERMINATED_UNSUCCESSFUL);
		} else {
			row.setState(AciPhysicalSizerConstants.TERMINATED_SUCCESSFUL);
		}
		rowRepository.saveAndFlush(row);
		return unterminatedRacks;
	}

	public void localTermination(RowTable row) {
		RowServices.localRow.get().setLocalTerminationStartTime(System.currentTimeMillis());
		for (RackTable rackTable : row.getRacks()) {
			terminatePortDomains(rackTable, rackTable.getAggPortDomainListD1(),
					getAvailableSwitchesForLocalTermination(rackTable, AciPhysicalSizerConstants.PORT_DOMAIN_D1));

			terminatePortDomains(rackTable, rackTable.getAggPortDomainListD2(),
					getAvailableSwitchesForLocalTermination(rackTable, AciPhysicalSizerConstants.PORT_DOMAIN_D2));

			terminatePortDomains(rackTable, rackTable.getAggPortDomainListD(),
					getAvailableSwitchesForLocalTermination(rackTable, AciPhysicalSizerConstants.PORT_DOMAIN_D));
		}
		RowServices.localRow.get().setLocalTerminationStopTime(System.currentTimeMillis());
	}

	private List<UnterminatedRacks> neighbourTermination(RowTable rowTable) {
		RowServices.localRow.get().setNeighbourTerminationStartTime(System.currentTimeMillis());
		List<UnterminatedRacks> unTerminatedRacks = new ArrayList<UnterminatedRacks>();

		for (RackTable rackTable : rowTable.getRacks()) {
			if (isUnterminated(rackTable)) {
				terminatePortDomains(rackTable, rackTable.getAggPortDomainListD1(),
						getAvailableSwitchesForNeighbourTermination(getAvailableRacks(rackTable, rowTable),
								AciPhysicalSizerConstants.PORT_DOMAIN_D1));

				terminatePortDomains(rackTable, rackTable.getAggPortDomainListD2(),
						getAvailableSwitchesForNeighbourTermination(getAvailableRacks(rackTable, rowTable),
								AciPhysicalSizerConstants.PORT_DOMAIN_D2));

				terminatePortDomains(rackTable, rackTable.getAggPortDomainListD(),
						getAvailableSwitchesForNeighbourTermination(getAvailableRacks(rackTable, rowTable),
								AciPhysicalSizerConstants.PORT_DOMAIN_D));
				if (isUnterminated(rackTable)) {
					rackTable.setTerminated(true);
					unTerminatedRacks.add(new UnterminatedRacks(rackTable.getId(), rackTable.getName()));
				}
			}
		}
		RowServices.localRow.get().setNeighbourTerminationStopTime(System.currentTimeMillis());
		return unTerminatedRacks;
	}

	public static int getIntOffset(RackTable o1) {
		return Integer.parseInt(o1.getName().split("-")[1]);
	}

	private boolean isUnterminated(RackTable rackTable) {
		if (isPortDomainTerminated(rackTable.getAggPortDomainListD1())) {
			if (isPortDomainTerminated(rackTable.getAggPortDomainListD2())) {
				if (isPortDomainTerminated(rackTable.getAggPortDomainListD())) {
					return false;
				}
			}
		}
		return true;

	}

	private boolean isPortDomainTerminated(Map<String, PortDomain> aggPortDomainListD) {
		for (String speed : aggPortDomainListD.keySet()) {
			PortDomain portDomain = aggPortDomainListD.get(speed);
			if (portDomain.getPortsToTerminate() > 0) {
				return false;
			}
		}
		return true;
	}

	private List<SwitchTable> getAvailableSwitchesForNeighbourTermination(List<RackTable> rackTables, String domain) {
		List<SwitchTable> availableSwitches = new ArrayList<SwitchTable>();
		for (RackTable rackTable : rackTables) {
			availableSwitches.addAll(getAvailableSwitchesForLocalTermination(rackTable, domain));
		}
		return availableSwitches;
	}

	private List<RackTable> getAvailableRacks(RackTable rackTable, RowTable rowTable) {
		int offset = getIntOffset(rackTable);
		List<RackTable> racks = new ArrayList<RackTable>();
		if (offset - 2 > 0) {
			racks.add(rackRepository.findRacksByName(AciPhysicalSizerConstants.RACK_PREFIX + (offset - 1),
					rowTable.getId()));
			racks.add(rackRepository.findRacksByName(AciPhysicalSizerConstants.RACK_PREFIX + (offset - 2),
					rowTable.getId()));
		} else if (offset - 1 > 0) {
			racks.add(rackRepository.findRacksByName(AciPhysicalSizerConstants.RACK_PREFIX + (offset - 1),
					rowTable.getId()));
		}

		if (offset + 2 <= rowTable.getRacks().size()) {
			racks.add(rackRepository.findRacksByName(AciPhysicalSizerConstants.RACK_PREFIX + (offset + 1),
					rowTable.getId()));
			racks.add(rackRepository.findRacksByName(AciPhysicalSizerConstants.RACK_PREFIX + (offset + 2),
					rowTable.getId()));
		} else if (offset + 1 <= rowTable.getRacks().size()) {
			racks.add(rackRepository.findRacksByName(AciPhysicalSizerConstants.RACK_PREFIX + (offset + 1),
					rowTable.getId()));
		}
		return racks;
	}

	private List<SwitchTable> getAvailableSwitchesForLocalTermination(RackTable rackTable, String domain) {
		List<SwitchTable> availableSwitches = new ArrayList<SwitchTable>();
		for (SwitchTable switchTable : rackTable.getSwitches()) {
			availableSwitches.add(switchTable);
		}
		return availableSwitches;
	}

	private void terminatePortDomains(RackTable rackTable, Map<String, PortDomain> aggPortDomainListRack,
			List<SwitchTable> availableSwitches) {
		if (aggPortDomainListRack != null) {
			for (String speed : aggPortDomainListRack.keySet()) {
				for (SwitchTable switchTable : availableSwitches) {
					PortDomain portDomainDevice = aggPortDomainListRack.get(speed);
					PortDomain portDomainSwitch = switchTable.getPortDomainD().get(speed);
					if (portDomainSwitch != null && portDomainDevice != null
							&& isTerminatePossible(switchTable, rackTable, portDomainDevice.getDomain(), speed)) {

						int portsToTerminateDevice = portDomainDevice.getPortsToTerminate();
						int portsToTerminateSwitch = portDomainSwitch.getPortsToTerminate();

						if (portsToTerminateSwitch != 0 && portsToTerminateDevice >= portsToTerminateSwitch) {

							portDomainDevice.setNumOfPortsTerminated(
									portDomainDevice.getNumOfPortsTerminated() + portsToTerminateSwitch);
							portDomainSwitch.setNumOfPortsTerminated(portDomainSwitch.getNumOfPorts());
							updateRackTorMapping(rackTable, switchTable, portsToTerminateSwitch, speed,
									portDomainDevice);
							switchTable.setSpineTerminationDone(!switchTable.isSpineTerminationDone());

						} else if (portsToTerminateDevice != 0 && portsToTerminateSwitch > portsToTerminateDevice) {

							portDomainSwitch.setNumOfPortsTerminated(
									portDomainSwitch.getNumOfPortsTerminated() + portsToTerminateDevice);
							portDomainDevice.setNumOfPortsTerminated(portDomainDevice.getNumOfPorts());
							updateRackTorMapping(rackTable, switchTable, portsToTerminateDevice, speed,
									portDomainDevice);
							switchTable.setSpineTerminationDone(!switchTable.isSpineTerminationDone());
						}
					}

					switchRepository.saveAndFlush(switchTable);
				}
			}
		}
	}

	private boolean isTerminatePossible(SwitchTable switchTable, RackTable rackTable, String domain, String speed) {
		RackTorMappingTable rackTorMappingTable = rackTorMappingRepository
				.findRackTorMappingByRackIDAndSwitchId(rackTable.getId(), switchTable.getId(), speed);
		if (rackTorMappingTable == null) {
			return true;
		}
		if (rackTorMappingTable.getDomain().equals(domain) || domain.equals(AciPhysicalSizerConstants.PORT_DOMAIN_D)) {
			return true;
		}
		return false;
	}

	private void updateRackTorMapping(RackTable rackTable, SwitchTable switchTable, int terminatedPorts, String speed,
			PortDomain portDomainDevice) {

		RackTorMappingTable rackTorMapping = rackTorMappingRepository.findRackTorMappingByRackIDSwitchIdAndDomain(
				rackTable.getId(), switchTable.getId(), speed, portDomainDevice.getDomain());
		if (rackTorMapping == null) {
			rackTorMapping = getNewRackTorMapping(rackTable, switchTable);
			rackTorMapping.setType(portDomainDevice.getType());
			rackTorMapping.setDomain(portDomainDevice.getDomain());
			rackTorMapping.setNumOfPortsTerminated(terminatedPorts);
			rackTorMapping.setSpeed(speed);
		} else {
			rackTorMapping.setNumOfPortsTerminated(rackTorMapping.getNumOfPortsTerminated() + terminatedPorts);
		}

		rackTorMappingRepository.save(rackTorMapping);

	}

	private RackTorMappingTable getNewRackTorMapping(RackTable rackTable, SwitchTable switchTable) {
		RackTorMappingTable rackTorMappingTable = new RackTorMappingTable();
		rackTorMappingTable.setRackTable(rackTable);
		rackTorMappingTable.setSwitchTable(switchTable);
		return rackTorMappingTable;
	}

	private void clearTerminatedStats(RowTable rowTable) {
		for (RackTable rack : rowTable.getRacks()) {
			rackTorMappingRepository.deleteByRackID(rack.getId());
			calculateAggregatePortDomainsRack(rack);
			for (SwitchTable switchTable : rack.getSwitches()) {
				calculatePortDomainSwitch(switchTable);
			}
		}

	}

	private void calculatePortDomainSwitch(SwitchTable src) {

		HashMap<String, PortDomain> portDomainD = new HashMap<String, PortDomain>();
		for (PortGroup iter : src.getPortGroups()) {
			deviceHelper.addPortDomain(portDomainD, iter, iter.getNumOfPorts(),
					AciPhysicalSizerConstants.PORT_DOMAIN_D);
		}
		src.setPortDomainD(portDomainD);
	}

	private void calculateAggregatePortDomainsRack(RackTable rackTable) {
		Map<String, PortDomain> agrregatePortDomaind1 = new HashMap<String, PortDomain>();
		Map<String, PortDomain> agrregatePortDomaind2 = new HashMap<String, PortDomain>();
		Map<String, PortDomain> agrregatePortDomaind = new HashMap<String, PortDomain>();
		for (ServerTable server : rackTable.getServers()) {
			if (!server.isUcsManaged()) {
				calculateAggregatePortDomain(agrregatePortDomaind, server.getPortDomainD());
				calculateAggregatePortDomain(agrregatePortDomaind1, server.getPortDomainD1());
				calculateAggregatePortDomain(agrregatePortDomaind2, server.getPortDomainD2());
			}
		}

		rackTable.setAggPortDomainListD(agrregatePortDomaind);
		rackTable.setAggPortDomainListD1(agrregatePortDomaind1);
		rackTable.setAggPortDomainListD2(agrregatePortDomaind2);
	}

	private void calculateAggregatePortDomain(Map<String, PortDomain> rackPortDomains,
			Map<String, PortDomain> serverPortdomains) {
		for (String speed : serverPortdomains.keySet()) {
			PortDomain portDomainRack = rackPortDomains.get(speed);
			PortDomain portDomainServer = serverPortdomains.get(speed);
			if (portDomainRack == null) {
				rackPortDomains.put(speed, portDomainServer);
			} else {
				portDomainRack.setNumOfPorts(portDomainRack.getNumOfPorts() + portDomainServer.getNumOfPorts());
			}
		}
	}

}

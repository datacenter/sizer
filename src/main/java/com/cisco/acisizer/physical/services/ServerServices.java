package com.cisco.acisizer.physical.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.PortGroup;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RackTorMappingTable;
import com.cisco.acisizer.domain.ServerTable;
import com.cisco.acisizer.domain.SwitchTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericCouldNotSaveException;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.helper.DeviceHelper;
import com.cisco.acisizer.physical.rest.models.ServerUi;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.cisco.acisizer.repo.DeviceTypeRepository;
import com.cisco.acisizer.repo.RackRepository;
import com.cisco.acisizer.repo.RackTorMappingRepository;
import com.cisco.acisizer.repo.ServerRepository;
import com.cisco.acisizer.repo.SwitchRepository;
import com.cisco.acisizer.util.ACISizerConstant;

@Service
public class ServerServices {

	public static final int INITIAL_VALUE_FOR_PORTS = 0;

	public static final String PORT_GROUP_1G = "1G";

	public static final String PORT_DOMAIN_2_TOR = "2-TOR";

	@Inject
	private RackRepository rackRepository;

	@Inject
	private ServerRepository serverRepository;

	@Inject
	private DeviceTypeRepository deviceTypeRepository;

	@Inject
	private DeviceHelper deviceHelper;

	@Inject
	private RackTorMappingRepository rackTorMappingRepository;

	@Inject
	private SwitchRepository switchRepository;

	public ServerTable addServer(int projectId, int roomId, int rowId, int rackId, ServerUi serverUi)
			throws AciEntityNotFound, GenericCouldNotSaveException, GenericInvalidDataException {

		validateIds(projectId, roomId, rowId, rackId);
		DeviceType deviceType = deviceTypeRepository.findOne(serverUi.getTemplateTypeId());
		if (null == deviceType) {
			throw new AciEntityNotFound("Could not find the device type");
		}

		ServerTable server = new ServerTable();
		RackTable rackTable = rackRepository.getOne(rackId);
		InventoryInfo inv = rackTable.getInventoryInfo();

		if (rackTable.isNetworkTypeRack() == true) {
			throw new GenericInvalidDataException("can't add server to network rack");
		}
		server.setRackTable(rackTable);
		server.setDeviceType(deviceType);
		server.setNumOfInstances(serverUi.getNumOfInstances());
		server.setPortGroups(serverUi.getPortGroups());
		server.setUcsManaged(serverUi.isUcsManaged());
		if (!server.isUcsManaged()) {
			calculatePortDomains(server);
		}

		checkRackUnitsAvailabilityToAddServer(serverUi, deviceType, inv, rackTable);

		if (rackTable.getServers() == null) {
			rackTable.setServers(new ArrayList<ServerTable>());
		}

		rackTable.getServers().add(server);
		calculateAggregatePortDomainsRack(rackTable);
		deviceHelper.calculateInventryInfo(rackTable);
		rackTable.getRow().setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		rackRepository.flush();
		return server;

	}

	private void checkRackUnitsAvailabilityToAddServer(ServerUi serverUi, DeviceType deviceType, InventoryInfo inv,
			RackTable rackTable) throws GenericInvalidDataException {
		int totalRus = serverUi.getNumOfInstances() * deviceType.getPhysicalStats().getNoOfRus();
		checkRackUnitsAvailability(totalRus, inv, rackTable, totalRus);
	}

	private void calculatePortDomains(ServerTable server) {
		for (PortGroup iter : server.getPortGroups()) {
			if (!PORT_GROUP_1G.equals(iter.getSpeed())) {
				int numOfPorts = iter.getNumOfPorts() * server.getNumOfInstances();
				if (iter.getPlacement().equals(PORT_DOMAIN_2_TOR)) {
					int numOfPortsD1 = numOfPorts / 2;
					int numOfPortsD2 = numOfPorts - numOfPortsD1;

					deviceHelper.addPortDomain(server.getPortDomainD1(), iter, numOfPortsD1,
							AciPhysicalSizerConstants.PORT_DOMAIN_D1);
					deviceHelper.addPortDomain(server.getPortDomainD2(), iter, numOfPortsD2,
							AciPhysicalSizerConstants.PORT_DOMAIN_D2);

				} else {
					deviceHelper.addPortDomain(server.getPortDomainD(), iter, numOfPorts,
							AciPhysicalSizerConstants.PORT_DOMAIN_D);
				}
			}
		}
	}

	public ServerTable updateServer(int projectId, int roomId, int rowId, int rackId, int serverId, ServerUi serverUi)
			throws AciEntityNotFound, GenericCouldNotSaveException, GenericInvalidDataException {
		validateIds(projectId, roomId, rowId, rackId);
		DeviceType deviceType = deviceTypeRepository.findOne(serverUi.getTemplateTypeId());
		RackTable rackTable = rackRepository.getOne(rackId);
		InventoryInfo inv = rackTable.getInventoryInfo();
		ServerTable server = serverRepository.findOne(serverId);
		checkRackUnitsAvailabilityToUpdateServer(serverUi, deviceType, inv, rackTable, server);
		server.setNumOfInstances(serverUi.getNumOfInstances());
		server.setPortGroups(serverUi.getPortGroups());
		server.setUcsManaged(serverUi.isUcsManaged());
		if (!server.isUcsManaged()) {
			calculatePortDomains(server);
		}
		calculateAggregatePortDomainsRack(server.getRackTable());
		deviceHelper.calculateInventryInfo(server.getRack());
		server.getRack().getRow().setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		if (null == serverRepository.save(server)) {
			throw new GenericCouldNotSaveException("Could not save server data");
		}

		return server;
	}

	@Transactional
	public ServerTable deleteServer(int projectId, int roomId, int rowId, int rackId, int serverId)
			throws AciEntityNotFound, GenericCouldNotSaveException {
		validateIds(projectId, roomId, rowId, rackId);
		RackTable rackTable = rackRepository.findOne(rackId);
		ServerTable server = serverRepository.findOne(serverId);
		updateSwitchTermination(server, rackTable);
		rackTable.getServers().remove(server);
		serverRepository.delete(serverId);

		rackTable.getRow().setState(AciPhysicalSizerConstants.TERMINATION_NOT_STARTED);
		calculateAggregatePortDomainsRack(rackTable);
		deviceHelper.calculateInventryInfo(server.getRack());

		rackRepository.save(rackTable);
		return server;
	}

	private void updateSwitchTermination(ServerTable server, RackTable rackTable) {
		updateRackTorMapping(server.getPortDomainD(), rackTable.getAggPortDomainListD(), rackTable,
				AciPhysicalSizerConstants.PORT_DOMAIN_D);
		updateRackTorMapping(server.getPortDomainD1(), rackTable.getAggPortDomainListD1(), rackTable,
				AciPhysicalSizerConstants.PORT_DOMAIN_D1);
		updateRackTorMapping(server.getPortDomainD2(), rackTable.getAggPortDomainListD2(), rackTable,
				AciPhysicalSizerConstants.PORT_DOMAIN_D2);
	}

	@Transactional
	private void updateRackTorMapping(Map<String, PortDomain> portdomainsServer,
			Map<String, PortDomain> portDomainsRack, RackTable rackTable, String domain) {
		for (String speed : portdomainsServer.keySet()) {
			List<RackTorMappingTable> rackTorMappingTables = rackTorMappingRepository
					.findRackTorMappingByRackId(rackTable.getId(), speed, domain);
			PortDomain portDomainServer = portdomainsServer.get(speed);
			int totalPortServer = portDomainServer.getNumOfPorts();

			PortDomain portDomainRack = portDomainsRack.get(speed);
			int totalUnterminatedPortsRack = portDomainRack.getPortsToTerminate();
			if (totalUnterminatedPortsRack < totalPortServer) {
				portDomainRack.setNumOfPortsTerminated(portDomainRack.getNumOfPorts() - totalPortServer);
				for (RackTorMappingTable rackTorMappingTable : rackTorMappingTables) {
					SwitchTable switchTable = rackTorMappingTable.getSwitchTable();
					if (totalPortServer > rackTorMappingTable.getNumOfPortsTerminated()) {
						PortDomain portDomainSwitch = switchTable.getPortDomainD().get(speed);
						portDomainSwitch.setNumOfPortsTerminated(portDomainSwitch.getNumOfPortsTerminated()
								- rackTorMappingTable.getNumOfPortsTerminated());
						totalPortServer = totalPortServer - rackTorMappingTable.getNumOfPortsTerminated();
						rackTorMappingRepository.delete(rackTorMappingTable);
						switchRepository.saveAndFlush(switchTable);

					} else {
						if (totalPortServer > INITIAL_VALUE_FOR_PORTS) {
							rackTorMappingTable.setNumOfPortsTerminated(
									rackTorMappingTable.getNumOfPortsTerminated() - totalPortServer);
							totalPortServer = INITIAL_VALUE_FOR_PORTS;
							rackTorMappingRepository.flush();

						}
					}
				}
			}

		}
	}

	public List<ServerTable> getServers(int projectId, int roomId, int rowId, int rackId) throws AciEntityNotFound {

		validateIds(projectId, roomId, rowId, rackId);
		return rackRepository.findOne(rackId).getServers();
	}

	private void validateIds(int projectId, int roomId, int rowId, int rackId) throws AciEntityNotFound {

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

	}

	private void checkRackUnitsAvailabilityToUpdateServer(ServerUi serverUi, DeviceType deviceType, InventoryInfo inv,
			RackTable rackTable, ServerTable server) throws GenericInvalidDataException {
		int totalRus = serverUi.getNumOfInstances() * deviceType.getPhysicalStats().getNoOfRus();
		int RequiredRUs = totalRus - server.getNumOfInstances() * deviceType.getPhysicalStats().getNoOfRus();
		if (RequiredRUs > 0) {
			checkRackUnitsAvailability(totalRus, inv, rackTable, RequiredRUs);
		}
	}

	private void checkRackUnitsAvailability(int totalRequiredRUs, InventoryInfo inv, RackTable rackTable,
			int RequiredRUs) throws GenericInvalidDataException {
		int totalAvailableRuServer = (int) Math.ceil(
				((double) inv.getTotalNumOfRus() * rackTable.getPolicy().getDetails().getRackRuUtilization()) / 100);

		int remainingRuServer = totalAvailableRuServer - inv.getRusConsumedServer();
		int remainingRuswitch = inv.getTotalNumOfRus() - inv.getRusConsumedSwitch();
		int totalRemainingRu = inv.getTotalNumOfRus() - inv.getRusConsumed();
		int availableRUs = Math.min(Math.min(remainingRuServer, remainingRuswitch), totalRemainingRu);

		if (availableRUs < 0) {
			availableRUs = 0; // insufficient rack space error message will not get availableRus in negative value by this.
		}

		if (RequiredRUs > availableRUs) {
			throw new GenericInvalidDataException(
					"Insufficient Rack Space." + " Available RUs (After applying policy constraint): " + availableRUs
							+ ", Required RUs: " + totalRequiredRUs);
		}
	}

	private void calculateAggregatePortDomainsRack(RackTable rackTable) {
		clearAggregatePortDomainValues(rackTable.getAggPortDomainListD());
		clearAggregatePortDomainValues(rackTable.getAggPortDomainListD1());
		clearAggregatePortDomainValues(rackTable.getAggPortDomainListD2());
		for (ServerTable server : rackTable.getServers()) {
			if (!server.isUcsManaged()) {
				calculateAggregatePortDomain(rackTable.getAggPortDomainListD(), server.getPortDomainD());
				calculateAggregatePortDomain(rackTable.getAggPortDomainListD1(), server.getPortDomainD1());
				calculateAggregatePortDomain(rackTable.getAggPortDomainListD2(), server.getPortDomainD2());
			}
		}

	}

	private void clearAggregatePortDomainValues(Map<String, PortDomain> aggPortDomainListD) {
		for (PortDomain portDomain : aggPortDomainListD.values()) {
			portDomain.setNumOfPorts(INITIAL_VALUE_FOR_PORTS);
		}
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

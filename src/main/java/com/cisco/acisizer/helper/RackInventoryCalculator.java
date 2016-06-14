/**
 * 
 */
package com.cisco.acisizer.helper;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.PortDomain;
import com.cisco.acisizer.domain.PortInventory;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.ServerTable;
import com.cisco.acisizer.domain.SwitchTable;

/**
 * @author Mahesh
 *
 */
@Component
public class RackInventoryCalculator {
	public static final int POWER_CONSTANT = 1000;
	public static final String LEAF = "Leaf";
	public static final String SPINE = "Spine";

	@Inject
	private DeviceTypeHelper deviceTypeHelper;

	public void calculateInventryInfo(RackTable rackTable) {
		InventoryInfo inventoryInfo = new InventoryInfo();
		deviceTypeHelper.initializeDeviceCounts(inventoryInfo);
		initializeTotalValues(rackTable, inventoryInfo);

		calculateServerInfo(rackTable, inventoryInfo);
		calculateSwitchInfo(rackTable, inventoryInfo);
		calculatePortInventoryDevice(rackTable, inventoryInfo);
		calculateTotalAndTerminatedPorts(inventoryInfo);
		rackTable.setInventoryInfo(inventoryInfo);
	}

	private void calculatePortInventoryDevice(RackTable rackTable, InventoryInfo inventoryInfo) {
		if (rackTable.getAggPortDomainListD() != null) {
			for (String speed : rackTable.getAggPortDomainListD().keySet()) {
				setPortInventoryServer(inventoryInfo, rackTable.getAggPortDomainListD().get(speed), speed);
			}
		}
		if (rackTable.getAggPortDomainListD1() != null) {
			for (String speed : rackTable.getAggPortDomainListD1().keySet()) {
				setPortInventoryServer(inventoryInfo, rackTable.getAggPortDomainListD1().get(speed), speed);
			}
		}
		if (rackTable.getAggPortDomainListD2() != null) {
			for (String speed : rackTable.getAggPortDomainListD2().keySet()) {
				setPortInventoryServer(inventoryInfo, rackTable.getAggPortDomainListD2().get(speed), speed);
			}
		}
	}

	private void initializeTotalValues(RackTable rackTable, InventoryInfo inventoryInfo) {
		inventoryInfo.setTotalCooling(rackTable.getRackType().getPowerCooling().getCoolingInBTU());
		inventoryInfo.setTotalPower(rackTable.getRackType().getPowerCooling().getPower());
		inventoryInfo.setTotalNumOfRacks(1);
		inventoryInfo.setTotalNumOfRus(rackTable.getRackType().getPhysicalStats().getNoOfRus());
	}

	/**
	 * @param inventoryInfo
	 */
	private void calculateTotalAndTerminatedPorts(InventoryInfo inventoryInfo) {
		int numOfTotalPorts = 0;
		int numOfTerminatedPorts = 0;
		for (PortInventory portInventory : inventoryInfo.getPortInventoryDevice()) {
			numOfTotalPorts += portInventory.getTotalPorts();
			numOfTerminatedPorts += portInventory.getTerminated();
		}
		inventoryInfo.setTotalNumOfPorts(numOfTotalPorts);
		inventoryInfo.setTotalNumOfPotsTerminated(numOfTerminatedPorts);
	}

	/**
	 * @param rackTable
	 * @param inventoryInfo
	 */
	private void calculateServerCounts(InventoryInfo inventoryInfo, ServerTable serverTable) {
		Integer count = inventoryInfo.getDeviceCounts().get(serverTable.getDeviceType().getType());
		inventoryInfo.setTotalDeviceCount(inventoryInfo.getTotalDeviceCount() + serverTable.getNumOfInstances());
		if (count != null) {
			inventoryInfo.getDeviceCounts().put(serverTable.getDeviceType().getType(),
					count + serverTable.getNumOfInstances());
		} else {
			inventoryInfo.getDeviceCounts().put(serverTable.getDeviceType().getType(), serverTable.getNumOfInstances());
		}

	}

	private void calculateSwitchCountsSpine(InventoryInfo inventoryInfo) {
		Integer count = inventoryInfo.getDeviceCounts().get(SPINE);
		if (count != null) {
			inventoryInfo.getDeviceCounts().put(SPINE, count + 1);
		} else {
			inventoryInfo.getDeviceCounts().put(SPINE, 1);
		}
	}

	private void calculateSwitchCountsLeaf(InventoryInfo inventoryInfo) {
		Integer count = inventoryInfo.getDeviceCounts().get(LEAF);
		if (count != null) {
			inventoryInfo.getDeviceCounts().put(LEAF, count + 1);
		} else {
			inventoryInfo.getDeviceCounts().put(LEAF, 1);
		}
	}

	/**
	 * @param rackTable
	 * @param inventoryInfo
	 * @return
	 */
	private void calculateSwitchInfo(RackTable rackTable, InventoryInfo inventoryInfo) {
		for (SwitchTable switchIter : rackTable.getSwitches()) {
			inventoryInfo.setRusConsumed(
					inventoryInfo.getRusConsumed() + switchIter.getDeviceType().getPhysicalStats().getNoOfRus());
			inventoryInfo.setRusConsumedSwitch(inventoryInfo.getRusConsumedSwitch() + switchIter.getDeviceType().getPhysicalStats().getNoOfRus());
			if (switchIter.getDeviceType().getType().equals(SPINE)) {
				calculatePortInventorySpine(inventoryInfo, switchIter);
				calculateSwitchCountsSpine(inventoryInfo);
			} else {
				calculatePortInventoryTor(inventoryInfo, switchIter);
				calculateSwitchCountsLeaf(inventoryInfo);
			}
			calculatePhysicalAndCoolingStatsSwitch(inventoryInfo, switchIter);
		}

	}

	private void calculatePortInventoryTor(InventoryInfo inventoryInfo, SwitchTable switchIter) {

		for (String speed : switchIter.getPortDomainD().keySet()) {
			setPortInventoryTor(inventoryInfo, switchIter.getPortDomainD().get(speed), speed);
		}
	}

	/**
	 * @param inventoryInfo
	 * @param switchIter
	 */
	private void calculatePortInventorySpine(InventoryInfo inventoryInfo, SwitchTable switchIter) {
		for (String speed : switchIter.getPortDomainD().keySet()) {
			setPortInventorySpine(inventoryInfo, switchIter.getPortDomainD().get(speed), speed);
		}
	}

	/**
	 * @param rackTable
	 * @return
	 */
	private void calculateServerInfo(RackTable rackTable, InventoryInfo inventoryInfo) {

		for (ServerTable server : rackTable.getServers()) {
			inventoryInfo.setRusConsumed(inventoryInfo.getRusConsumed()
					+ server.getDeviceType().getPhysicalStats().getNoOfRus() * server.getNumOfInstances());
			inventoryInfo.setRusConsumedServer(inventoryInfo.getRusConsumedServer()
					+ server.getDeviceType().getPhysicalStats().getNoOfRus() * server.getNumOfInstances());
			calculateServerCounts(inventoryInfo, server);
			// calculatePortInventoryServer(inventoryInfo, server);
			calculatePhysicalAndCoolingStatsDevice(inventoryInfo, server);
		}

	}

	/**
	 * @param inventoryInfo
	 * @param server
	 */
	private void calculatePhysicalAndCoolingStatsDevice(InventoryInfo inventoryInfo, ServerTable server) {
		inventoryInfo.setCurrentWeight(inventoryInfo.getCurrentWeight()
				+ server.getDeviceType().getPhysicalStats().getWeight() * server.getNumOfInstances());
		inventoryInfo.setPowerConsumed(inventoryInfo.getPowerConsumed()
				+ (server.getDeviceType().getPowerCooling().getPower() * server.getNumOfInstances()) / POWER_CONSTANT);
		inventoryInfo.setCoolingConsumed(inventoryInfo.getCoolingConsumed()
				+ server.getDeviceType().getPowerCooling().getCoolingInBTU() * server.getNumOfInstances());
	}

	private void calculatePhysicalAndCoolingStatsSwitch(InventoryInfo inventoryInfo, SwitchTable switchTemp) {
		inventoryInfo.setCurrentWeight(
				inventoryInfo.getCurrentWeight() + switchTemp.getDeviceType().getPhysicalStats().getWeight());
		inventoryInfo.setPowerConsumed(inventoryInfo.getPowerConsumed()
				+ (switchTemp.getDeviceType().getPowerCooling().getPower()) / POWER_CONSTANT);
		inventoryInfo.setCoolingConsumed(
				inventoryInfo.getCoolingConsumed() + switchTemp.getDeviceType().getPowerCooling().getCoolingInBTU());

	}

	/**
	 * @param inventoryInfo
	 * @param server
	 *//*
		 * private void calculatePortInventoryServer(InventoryInfo
		 * inventoryInfo, ServerTable server) { for (String speed :
		 * server.getPortDomainD().keySet()) {
		 * setPortInventoryServer(inventoryInfo,
		 * server.getPortDomainD().get(speed), speed); }
		 * 
		 * for (String speed : server.getPortDomainD1().keySet()) {
		 * setPortInventoryServer(inventoryInfo,
		 * server.getPortDomainD1().get(speed), speed); }
		 * 
		 * for (String speed : server.getPortDomainD2().keySet()) {
		 * setPortInventoryServer(inventoryInfo,
		 * server.getPortDomainD2().get(speed), speed); } }
		 */

	/**
	 * @param portDomain
	 * @param portInventory
	 */
	private void setPortInventory(PortDomain portDomain, PortInventory portInventory) {
		portInventory.setTotalPorts(portInventory.getTotalPorts() + portDomain.getNumOfPorts());
		portInventory.setTerminated(portInventory.getTerminated() + portDomain.getNumOfPortsTerminated());
	}

	/**
	 * @param inventoryInfo
	 * @param portDomain
	 */
	private void setPortInventoryServer(InventoryInfo inventoryInfo, PortDomain portDomain, String speed) {
		PortInventory portInventory = InventoryHelper.getPortInventryByTypeServer(inventoryInfo, speed);
		setPortInventory(portDomain, portInventory);
	}

	/**
	 * @param inventoryInfo
	 * @param portDomain
	 */
	private void setPortInventoryTor(InventoryInfo inventoryInfo, PortDomain portDomain, String speed) {
		PortInventory portInventory = getPortInventryByTypeTor(inventoryInfo, speed);
		setPortInventory(portDomain, portInventory);
	}

	private void setPortInventorySpine(InventoryInfo inventoryInfo, PortDomain portDomain, String speed) {
		PortInventory portInventory = getPortInventryByTypeSpine(inventoryInfo, speed);
		setPortInventory(portDomain, portInventory);
	}

	private PortInventory getPortInventryByTypeTor(InventoryInfo inventoryInfo, String type) {
		for (PortInventory portInventory : inventoryInfo.getPortInventoryLeaf()) {
			if (portInventory.getType().equals(type)) {
				return portInventory;
			}
		}
		PortInventory portInventory = new PortInventory(type);
		inventoryInfo.getPortInventoryLeaf().add(portInventory);
		return portInventory;

	}

	private PortInventory getPortInventryByTypeSpine(InventoryInfo inventoryInfo, String type) {
		for (PortInventory portInventory : inventoryInfo.getPortInventorySpines()) {
			if (portInventory.getType().equals(type)) {
				return portInventory;
			}
		}
		PortInventory portInventory = new PortInventory(type);
		inventoryInfo.getPortInventorySpines().add(portInventory);
		return portInventory;

	}

}

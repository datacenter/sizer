package com.cisco.acisizer.helper;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.PortInventory;

public class InventoryHelper {

	public static void addInventory(InventoryInfo to, InventoryInfo add) {

		if (null == to || null == add)
			return;

		addDeviceCounts(to, add);
		to.setPowerConsumed(to.getPowerConsumed() + add.getPowerConsumed());
		to.setCurrentWeight(to.getCurrentWeight()+add.getCurrentWeight());
		to.setCoolingConsumed(to.getCoolingConsumed()+add.getCoolingConsumed());
		to.setRusConsumed(to.getRusConsumed() + add.getRusConsumed());
		to.setTotalNumOfRacks(to.getTotalNumOfRacks() + add.getTotalNumOfRacks());
		to.setTotalNumOfRus(to.getTotalNumOfRus() + add.getTotalNumOfRus());
		to.setTotalDeviceCount(to.getTotalDeviceCount()+add.getTotalDeviceCount());
		to.setTotalPower(to.getTotalPower()+add.getTotalPower());
		to.setTotalNumOfRows(to.getTotalNumOfRows() + add.getTotalNumOfRows());
        to.setTotalCooling(to.getTotalCooling() + add.getTotalCooling());
        to.setTotalNumOfPorts(to.getTotalNumOfPorts()+add.getTotalNumOfPorts());
        to.setTotalNumOfPotsTerminated(to.getTotalNumOfPotsTerminated()+add.getTotalNumOfPotsTerminated());

		for (PortInventory portInventory : add.getPortInventoryDevice()) {
			addPortInventoryServer(to, portInventory);
		}
		for (PortInventory portInventory : add.getPortInventorySpines()) {
			addPortInventorySpine(to, portInventory);
		}
		for (PortInventory portInventory : add.getPortInventoryLeaf()) {
			addPortInventoryTor(to, portInventory);
		}

	}

	private static void addDeviceCounts(InventoryInfo to, InventoryInfo add) {
		for (String device : add.getDeviceCounts().keySet()) {
			if (to.getDeviceCounts().get(device) != null) {
				to.getDeviceCounts().put(device, to.getDeviceCounts().get(device) + add.getDeviceCounts().get(device));
			} else {
				to.getDeviceCounts().put(device, add.getDeviceCounts().get(device));
			}

		}

	}

	public static PortInventory getPortInventryByTypeServer(InventoryInfo inventoryInfo, String type) {
		for (PortInventory portInventory : inventoryInfo.getPortInventoryDevice()) {
			if (portInventory.getType().equals(type)) {
				return portInventory;
			}
		}
		PortInventory portInventory = new PortInventory(type);
		inventoryInfo.getPortInventoryDevice().add(portInventory);
		return portInventory;

	}

	public static PortInventory getPortInventryByTypeSpine(InventoryInfo inventoryInfo, String type) {
		for (PortInventory portInventory : inventoryInfo.getPortInventorySpines()) {
			if (portInventory.getType().equals(type)) {
				return portInventory;
			}
		}
		PortInventory portInventory = new PortInventory(type);
		inventoryInfo.getPortInventorySpines().add(portInventory);
		return portInventory;

	}

	public static PortInventory getPortInventryByTypeTor(InventoryInfo inventoryInfo, String type) {
		for (PortInventory portInventory : inventoryInfo.getPortInventoryLeaf()) {
			if (portInventory.getType().equals(type)) {
				return portInventory;
			}
		}
		PortInventory portInventory = new PortInventory(type);
		inventoryInfo.getPortInventoryLeaf().add(portInventory);
		return portInventory;

	}

	public static void addPortInventory(PortInventory to, PortInventory src) {
		to.setTotalPorts(to.getTotalPorts() + src.getTotalPorts());
		to.setTerminated(to.getTerminated() + src.getTerminated());
	}

	/**
	 * @param inventoryInfo
	 * @param portDomain
	 */
	public static void addPortInventoryServer(InventoryInfo inventoryInfo, PortInventory portInventoryAdd) {
		PortInventory portInventoryTo = getPortInventryByTypeServer(inventoryInfo, portInventoryAdd.getType());
		addPortInventory(portInventoryTo, portInventoryAdd);
	}

	/**
	 * @param inventoryInfo
	 * @param portDomain
	 */
	public static void addPortInventorySpine(InventoryInfo inventoryInfo, PortInventory portInventoryAdd) {
		PortInventory portInventoryTo = getPortInventryByTypeSpine(inventoryInfo, portInventoryAdd.getType());
		addPortInventory(portInventoryTo, portInventoryAdd);
	}

	/**
	 * @param inventoryInfo
	 * @param portDomain
	 */
	public static void addPortInventoryTor(InventoryInfo inventoryInfo, PortInventory portInventoryAdd) {
		PortInventory portInventoryTo = getPortInventryByTypeTor(inventoryInfo, portInventoryAdd.getType());
		addPortInventory(portInventoryTo, portInventoryAdd);
	}

}

package com.cisco.acisizer.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cisco.acisizer.physical.util.CustomDoubleSerializer;
import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;



public class InventoryInfo {

	@JsonView(View.Room.class)
	private Map<String, Integer> deviceCounts;

	@JsonView(View.Room.class)
	private List<PortInventory> portInventoryDevice;

	@JsonView(View.Room.class)
	private List<PortInventory> portInventoryLeaf;

	@JsonView(View.Room.class)
	@JsonSerialize(using = CustomDoubleSerializer.class)
	private double powerConsumed;

	@JsonView(View.Room.class)
	private int rusConsumed;
	
	private int rusConsumedServer;
	
	private int rusConsumedSwitch;

	@JsonView(View.Room.class)
	private double currentWeight;

	@JsonView(View.Room.class)
	private List<PortInventory> portInventorySpines;

	@JsonView(View.Room.class)
	private int totalNumOfRacks;

	@JsonView(View.Room.class)
	private int totalNumOfRus;

	@JsonView(View.Room.class)
	private int totalNumOfPorts;

	@JsonView(View.Room.class)
	private int totalNumOfPotsTerminated;

	@JsonView(View.Room.class)
	private int totalDeviceCount;

	@JsonView(View.Room.class)
	private double totalPower;

	@JsonView(View.Room.class)
	private int totalCooling;
	
	@JsonView(View.Room.class)
	private int totalNumOfRows;
	
	@JsonView(View.Room.class)
	private int totalNumOfRooms;
	
	
	public int getTotalNumOfRooms() {
		return totalNumOfRooms;
	}

	public void setTotalNumOfRooms(int totalNumOfRooms) {
		this.totalNumOfRooms = totalNumOfRooms;
	}
	
	public int getTotalNumOfRows() {
		return totalNumOfRows;
	}

	public void setTotalNumOfRows(int totalNumOfRows) {
		this.totalNumOfRows = totalNumOfRows;
	}

	/**
	 * @return the totalCooling
	 */
	public int getTotalCooling() {
		return totalCooling;
	}

	/**
	 * @param totalCooling
	 *            the totalCooling to set
	 */
	public void setTotalCooling(int totalCooling) {
		this.totalCooling = totalCooling;
	}

	/**
	 * @return the coolingConsumed
	 */
	public int getCoolingConsumed() {
		return coolingConsumed;
	}

	/**
	 * @param coolingConsumed
	 *            the coolingConsumed to set
	 */
	public void setCoolingConsumed(int coolingConsumed) {
		this.coolingConsumed = coolingConsumed;
	}

	@JsonView(View.Room.class)
	private int coolingConsumed;

	/**
	 * @return the totalPowerConsumed
	 */
	public double getTotalPower() {
		return totalPower;
	}

	/**
	 * @param totalPowerConsumed
	 *            the totalPowerConsumed to set
	 */
	public void setTotalPower(double totalPowerConsumed) {
		this.totalPower = totalPowerConsumed;
	}

	public InventoryInfo() {
		portInventoryDevice = new ArrayList<PortInventory>();
		portInventoryLeaf = new ArrayList<PortInventory>();
		portInventorySpines = new ArrayList<PortInventory>();
		deviceCounts = new LinkedHashMap<String, Integer>();
	}

	public List<PortInventory> getPortInventoryDevice() {
		return portInventoryDevice;
	}

	public void setPortInventoryDevice(List<PortInventory> portInventoryDevice) {
		this.portInventoryDevice = portInventoryDevice;
	}

	public List<PortInventory> getPortInventoryLeaf() {
		return portInventoryLeaf;
	}

	public void setPortInventoryLeaf(List<PortInventory> portInventoryTors) {
		this.portInventoryLeaf = portInventoryTors;
	}

	public double getPowerConsumed() {
		return powerConsumed;
	}

	public void setPowerConsumed(double powerConsumed) {
		this.powerConsumed = powerConsumed;
	}

	public int getRusConsumed() {
		return rusConsumed;
	}

	public void setRusConsumed(int rusConsumed) {
		this.rusConsumed = rusConsumed;
	}

	public double getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(double currentWeight) {
		this.currentWeight = currentWeight;
	}

	public List<PortInventory> getPortInventorySpines() {
		return portInventorySpines;
	}

	public void setPortInventorySpines(List<PortInventory> portInventorySpines) {
		this.portInventorySpines = portInventorySpines;
	}

	public int getTotalNumOfRacks() {
		return totalNumOfRacks;
	}

	public void setTotalNumOfRacks(int totalNumOfRacks) {
		this.totalNumOfRacks = totalNumOfRacks;
	}

	public int getTotalNumOfRus() {
		return totalNumOfRus;
	}

	public void setTotalNumOfRus(int totalNumOfRus) {
		this.totalNumOfRus = totalNumOfRus;
	}

	/**
	 * @return the totalNumOfPorts
	 */
	public int getTotalNumOfPorts() {
		return totalNumOfPorts;
	}

	/**
	 * @param totalNumOfPorts
	 *            the totalNumOfPorts to set
	 */
	public void setTotalNumOfPorts(int totalNumOfPorts) {
		this.totalNumOfPorts = totalNumOfPorts;
	}

	/**
	 * @return the totalNumOfPotsTerminated
	 */
	public int getTotalNumOfPotsTerminated() {
		return totalNumOfPotsTerminated;
	}

	/**
	 * @param totalNumOfPotsTerminated
	 *            the totalNumOfPotsTerminated to set
	 */
	public void setTotalNumOfPotsTerminated(int totalNumOfPotsTerminated) {
		this.totalNumOfPotsTerminated = totalNumOfPotsTerminated;
	}

	/**
	 * @return the deviceCounts
	 */
	public Map<String, Integer> getDeviceCounts() {
		return deviceCounts;
	}

	/**
	 * @param deviceCounts
	 *            the deviceCounts to set
	 */
	public void setDeviceCounts(Map<String, Integer> deviceCounts) {
		this.deviceCounts = deviceCounts;
	}

	/**
	 * @return the totalDeviceCount
	 */
	public int getTotalDeviceCount() {
		return totalDeviceCount;
	}

	/**
	 * @param totalDeviceCount
	 *            the totalDeviceCount to set
	 */
	public void setTotalDeviceCount(int totalDeviceCount) {
		this.totalDeviceCount = totalDeviceCount;
	}

	/**
	 * @return the rusConsumedServer
	 */
	public int getRusConsumedServer() {
		return rusConsumedServer;
	}

	/**
	 * @param rusConsumedServer the rusConsumedServer to set
	 */
	public void setRusConsumedServer(int rusConsumedServer) {
		this.rusConsumedServer = rusConsumedServer;
	}

	/**
	 * @return the rusConsumedSwitch
	 */
	public int getRusConsumedSwitch() {
		return rusConsumedSwitch;
	}

	/**
	 * @param rusConsumedSwitch the rusConsumedSwitch to set
	 */
	public void setRusConsumedSwitch(int rusConsumedSwitch) {
		this.rusConsumedSwitch = rusConsumedSwitch;
	}
}

package com.cisco.acisizer.domain;

import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.cisco.acisizer.physical.rest.models.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.reflect.TypeToken;

@Entity
@Table(name = "device_type")
public class DeviceType extends AciDomain {

	@Id
	@GenericGenerator(name = "seq_id", strategy = "com.cisco.acisizer.util.GenericIdGenerator")
	@GeneratedValue(generator = "seq_id")
	@Column(name = "id")
	@JsonView(View.Rows.class)
	private int id;

	@Column(name = "name")
	@JsonView(View.Rows.class)
	private String name;

	@Embedded
	@JsonView(View.Rows.class)
	private PhysicalStatsTable physicalStats;

	@Embedded
	@JsonView(View.Rows.class)
	private PowerCoolingTable powerCooling;

	@Column(name = "fans")
	@JsonView(View.Rows.class)
	private int fans;

	@Column(name = "no_of_usb")
	@JsonView(View.Rows.class)
	private int numOfUsbPorts;

	@Column(name = "no_of_vga")
	@JsonView(View.Rows.class)
	private int numOfVgaPorts;

	@Column(name = "no_of_kvm")
	@JsonView(View.Rows.class)
	private int numOfKvmConnectors;

	@Column(name = "type")
	@JsonView(View.Rows.class)
	private String type;

	@Column(name = "default_port_group")
	@JsonView(View.Rows.class)
	private String defaultPortGroup;

	@Column(name = "description")
	@JsonView(View.Rows.class)
	private String description;

	@Column(name = "color")
	@JsonView(View.Rows.class)
	private String color;

	@Column(name = "order_no")
	@JsonView(View.Rows.class)
	private int orderNo;

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public List<PortGroup> getDefaultPortGroup() {
		Type type = new TypeToken<List<PortGroup>>() {
		}.getType();
		List<PortGroup> list = gson.fromJson(this.defaultPortGroup, type);
		return list;
	}

	public void setDefaultPortGroup(List<PortGroup> portGroups) {
		this.defaultPortGroup = gson.toJson(portGroups);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the physicalStats
	 */
	public PhysicalStatsTable getPhysicalStats() {
		return physicalStats;
	}

	/**
	 * @param physicalStats
	 *            the physicalStats to set
	 */
	public void setPhysicalStats(PhysicalStatsTable physicalStats) {
		this.physicalStats = physicalStats;
	}

	/**
	 * @return the powerCooling
	 */
	public PowerCoolingTable getPowerCooling() {
		return powerCooling;
	}

	/**
	 * @param powerCooling
	 *            the powerCooling to set
	 */
	public void setPowerCooling(PowerCoolingTable powerCooling) {
		this.powerCooling = powerCooling;
	}

	/**
	 * @return the fans
	 */
	public int getFans() {
		return fans;
	}

	/**
	 * @param fans
	 *            the fans to set
	 */
	public void setFans(int fans) {
		this.fans = fans;
	}

	/**
	 * @return the numOfUsbPorts
	 */
	public int getNumOfUsbPorts() {
		return numOfUsbPorts;
	}

	/**
	 * @param numOfUsbPorts
	 *            the numOfUsbPorts to set
	 */
	public void setNumOfUsbPorts(int numOfUsbPorts) {
		this.numOfUsbPorts = numOfUsbPorts;
	}

	/**
	 * @return the numOfVgaPorts
	 */
	public int getNumOfVgaPorts() {
		return numOfVgaPorts;
	}

	/**
	 * @param numOfVgaPorts
	 *            the numOfVgaPorts to set
	 */
	public void setNumOfVgaPorts(int numOfVgaPorts) {
		this.numOfVgaPorts = numOfVgaPorts;
	}

	/**
	 * @return the numOfKvmConnectors
	 */
	public int getNumOfKvmConnectors() {
		return numOfKvmConnectors;
	}

	/**
	 * @param numOfKvmConnectors
	 *            the numOfKvmConnectors to set
	 */
	public void setNumOfKvmConnectors(int numOfKvmConnectors) {
		this.numOfKvmConnectors = numOfKvmConnectors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

}
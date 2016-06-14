package com.cisco.acisizer.domain;

import com.cisco.acisizer.physical.rest.models.View;
import com.fasterxml.jackson.annotation.JsonView;

public class PortInventory {

	@JsonView(View.Room.class)
	private String type;

	@JsonView(View.Room.class)
	private int terminated;

	@JsonView(View.Room.class)
	private int totalPorts;

	public PortInventory(String type) {
		this.type = type;
	}

	/**
	 * @return the terminated
	 */
	public int getTerminated() {
		return terminated;
	}

	/**
	 * @param terminated
	 *            the terminated to set
	 */
	public void setTerminated(int terminated) {
		this.terminated = terminated;
	}

	/**
	 * @return the totalPorts
	 */
	public int getTotalPorts() {
		return totalPorts;
	}

	/**
	 * @param totalPorts
	 *            the totalPorts to set
	 */
	public void setTotalPorts(int totalPorts) {
		this.totalPorts = totalPorts;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
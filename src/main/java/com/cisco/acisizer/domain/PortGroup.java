package com.cisco.acisizer.domain;

import com.cisco.acisizer.physical.rest.models.View;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.fasterxml.jackson.annotation.JsonView;

public class PortGroup {

	@JsonView(View.Rows.class)
	private String name;

	@JsonView(View.Rows.class)
	private String type;

	@JsonView(View.Rows.class)
	private String speed;

	@JsonView(View.Rows.class)
	private int numOfPorts;

	@JsonView(View.Rows.class)
	private String redundancyModel;

	@JsonView(View.Rows.class)
	private String placement;

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public int getNumOfPorts() {
		return numOfPorts;
	}

	public void setNumOfPorts(int numOfPorts) {
		this.numOfPorts = numOfPorts;
	}

	public String getRedundancyModel() {
		return redundancyModel;
	}

	public void setRedundancyModel(String redundancyModel) {
		this.redundancyModel = redundancyModel;
	}
	
	public int getSpeedInt(){
		return Integer.parseInt(this.speed.split(AciPhysicalSizerConstants.SPEED_UNIT_G)[0]);
	}

}
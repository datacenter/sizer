package com.cisco.acisizer.domain;

import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

public class PolicyDetail {

	@JsonView(View.Room.class)
	private double growthPolicy;

	@JsonView(View.Room.class)
	private double leafOversubscription;

	@JsonView(View.Room.class)
	private String cableType;

	@JsonView(View.Room.class)
	private int leafsPerRack;

	@JsonView(View.Room.class)
	private double leafUtilization;

	@JsonView(View.Room.class)
	private double rackRuUtilization;

	@JsonView(View.Room.class)
	private double rackPowerUtilization;

	@JsonView(View.Room.class)
	private String preferredAciLeaf;

	private int preferredAciLeafId;

	@JsonView(View.Room.class)
	private String preferredAciSpine;

	private int preferredAciSpineId;

	@JsonView(View.Room.class)
	private String preferredN9kLeaf;

	private int preferredN9kLeafId;

	@JsonView(View.Room.class)
	private String preferredN9kSpine;

	private int preferredN9kSpineId;

	public double getGrowthPolicy() {
		return growthPolicy;
	}

	public void setGrowthPolicy(double growthPolicy) {
		this.growthPolicy = growthPolicy;
	}

	public double getLeafOversubscription() {
		return leafOversubscription;
	}

	public void setLeafOversubscription(double leafOversubscription) {
		this.leafOversubscription = leafOversubscription;
	}

	public String getCableType() {
		return cableType;
	}

	public void setCableType(String cableType) {
		this.cableType = cableType;
	}

	public int getLeafsPerRack() {
		return leafsPerRack;
	}

	public void setLeafsPerRack(int leafsPerRack) {
		this.leafsPerRack = leafsPerRack;
	}

	public double getLeafUtilization() {
		return leafUtilization;
	}

	public void setLeafUtilization(double leafUtilization) {
		this.leafUtilization = leafUtilization;
	}

	public double getRackRuUtilization() {
		return rackRuUtilization;
	}

	public void setRackRuUtilization(double rackRuUtilization) {
		this.rackRuUtilization = rackRuUtilization;
	}

	public double getRackPowerUtilization() {
		return rackPowerUtilization;
	}

	public void setRackPowerUtilization(double rackPowerUtilization) {
		this.rackPowerUtilization = rackPowerUtilization;
	}

	public String getPreferredAciLeaf() {
		return preferredAciLeaf;
	}

	public void setPreferredAciLeaf(String preferredAciLeaf) {
		this.preferredAciLeaf = preferredAciLeaf;
	}

	public String getPreferredAciSpine() {
		return preferredAciSpine;
	}

	public void setPreferredAciSpine(String preferredAciSpine) {
		this.preferredAciSpine = preferredAciSpine;
	}

	public String getPreferredN9kLeaf() {
		return preferredN9kLeaf;
	}

	public void setPreferredN9kLeaf(String preferredN9kLeaf) {
		this.preferredN9kLeaf = preferredN9kLeaf;
	}

	public String getPreferredN9kSpine() {
		return preferredN9kSpine;
	}

	public void setPreferredN9kSpine(String preferredN9kSpine) {
		this.preferredN9kSpine = preferredN9kSpine;
	}

	public int getPreferredAciLeafId() {
		return preferredAciLeafId;
	}

	public void setPreferredAciLeafId(int preferredAciLeafId) {
		this.preferredAciLeafId = preferredAciLeafId;
	}

	public int getPreferredAciSpineId() {
		return preferredAciSpineId;
	}

	public void setPreferredAciSpineId(int preferredAciSpineId) {
		this.preferredAciSpineId = preferredAciSpineId;
	}

	public int getPreferredN9kLeafId() {
		return preferredN9kLeafId;
	}

	public void setPreferredN9kLeafId(int preferredN9kLeafId) {
		this.preferredN9kLeafId = preferredN9kLeafId;
	}

	public int getPreferredN9kSpineId() {
		return preferredN9kSpineId;
	}

	public void setPreferredN9kSpineId(int preferredN9kSpineId) {
		this.preferredN9kSpineId = preferredN9kSpineId;
	}

}

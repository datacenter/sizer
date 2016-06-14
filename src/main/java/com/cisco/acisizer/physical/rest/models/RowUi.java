package com.cisco.acisizer.physical.rest.models;

public class RowUi {
	private int noOfRacks;
	private int rackTypeId;
	private int policyId;
	private boolean policyInherited;
	
	public int getNoOfRacks() {
		return noOfRacks;
	}
	public void setNoOfRacks(int noOfRacks) {
		this.noOfRacks = noOfRacks;
	}
	public int getRackTypeId() {
		return rackTypeId;
	}
	public void setRackTypeId(int rackTypeId) {
		this.rackTypeId = rackTypeId;
	}
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	public boolean isPolicyInherited() {
		return policyInherited;
	}
	public void setPolicyInherited(boolean policyInherited) {
		this.policyInherited = policyInherited;
	}
	
}

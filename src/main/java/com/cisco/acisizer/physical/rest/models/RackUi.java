package com.cisco.acisizer.physical.rest.models;

public class RackUi {
	private int rackTypeId;
	private int policyId;
	private boolean policyInherited;
	private boolean isNetworkTypeRack;

	
	public boolean getisNetworkTypeRack() {
		return isNetworkTypeRack;
	}
	public void setNetworkTypeRack(boolean isNetworkTypeRack) {
		this.isNetworkTypeRack = isNetworkTypeRack;
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

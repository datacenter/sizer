package com.cisco.acisizer.summary;

import java.util.ArrayList;

public class SummaryUIResource {
	private int id=-1;
	private String Switch;
	
	private int VRFs=-1;
	private int BDs=-1;
	private int EPGs=-1;
	private int Endpoints=-1;
	private int Contracts=-1;
	private int L3Out=-1;
	private ArrayList<SummaryUIUtilizationDetail> utilizationDetails = new ArrayList<SummaryUIUtilizationDetail>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSwitch() {
		return Switch;
	}
	public void setSwitch(String switch1) {
		Switch = switch1;
	}
	
	public int getVRFs() {
		return VRFs;
	}
	public void setVRFs(int vRFs) {
		VRFs = vRFs;
	}
	public int getBDs() {
		return BDs;
	}
	public void setBDs(int bDs) {
		BDs = bDs;
	}
	public int getEPGs() {
		return EPGs;
	}
	public void setEPGs(int ePGs) {
		EPGs = ePGs;
	}
	public int getEndpoints() {
		return Endpoints;
	}
	public void setEndpoints(int endpoints) {
		Endpoints = endpoints;
	}
	public int getContracts() {
		return Contracts;
	}
	public void setContracts(int contracts) {
		Contracts = contracts;
	}
	public int getL3Out() {
		return L3Out;
	}
	public void setL3Out(int l3Out) {
		L3Out = l3Out;
	}
	public ArrayList<SummaryUIUtilizationDetail> getUtilizationDetails() {
		return utilizationDetails;
	}
	public void setUtilizationDetails(
			ArrayList<SummaryUIUtilizationDetail> utilizationDetails) {
		this.utilizationDetails = utilizationDetails;
	}
}

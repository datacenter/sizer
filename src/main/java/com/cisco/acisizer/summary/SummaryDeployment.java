package com.cisco.acisizer.summary;

import java.util.ArrayList;

public class SummaryDeployment {

	private ArrayList<SummaryL3out> l3outs = new ArrayList<SummaryL3out>();
	private ArrayList<String> l2outs = new ArrayList<String>();
	private ArrayList<SummaryEpg> epgs = new ArrayList<SummaryEpg>();
	public ArrayList<SummaryL3out> getL3outs() {
		return l3outs;
	}
	public void setL3outs(ArrayList<SummaryL3out> l3outs) {
		this.l3outs = l3outs;
	}
	public ArrayList<String> getL2outs() {
		return l2outs;
	}
	public void setL2outs(ArrayList<String> l2outs) {
		this.l2outs = l2outs;
	}
	public ArrayList<SummaryEpg> getEpgs() {
		return epgs;
	}
	public void setEpgs(ArrayList<SummaryEpg> epgs) {
		this.epgs = epgs;
	}
}

package com.cisco.acisizer.summary;

import java.util.ArrayList;

public class SummaryUtilization {
	private ArrayList<SummaryResource> resources = new ArrayList<SummaryResource>();
	private ArrayList<SummaryLeaf> leafs = new ArrayList<SummaryLeaf>();
	public ArrayList<SummaryResource> getResources() {
		return resources;
	}
	public void setResources(ArrayList<SummaryResource> resources) {
		this.resources = resources;
	}
	public ArrayList<SummaryLeaf> getLeafs() {
		return leafs;
	}
	public void setLeafs(ArrayList<SummaryLeaf> leafs) {
		this.leafs = leafs;
	}
}

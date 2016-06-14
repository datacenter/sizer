package com.cisco.acisizer.summary;

import java.util.ArrayList;

public class SummaryL3out {
	
	private String name;
	private int count;
	private int span;
	private ArrayList<SummaryInstanceDeployment> instances = new ArrayList<SummaryInstanceDeployment>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSpan() {
		return span;
	}
	public void setSpan(int span) {
		this.span = span;
	}
	public ArrayList<SummaryInstanceDeployment> getInstances() {
		return instances;
	}
	public void setInstances(ArrayList<SummaryInstanceDeployment> instances) {
		this.instances = instances;
	}

}

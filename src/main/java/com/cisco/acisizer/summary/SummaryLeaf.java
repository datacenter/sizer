package com.cisco.acisizer.summary;

import java.util.ArrayList;
import java.util.List;

public class SummaryLeaf {
	private String name;
	private List<String> labels = new ArrayList<String>();
	private ArrayList<SummaryResource> resources = new ArrayList<SummaryResource>();
	private String model;
	
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public ArrayList<SummaryResource> getResources() {
		return resources;
	}
	public void setResources(ArrayList<SummaryResource> resources) {
		this.resources = resources;
	}
}

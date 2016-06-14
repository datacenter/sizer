package com.cisco.acisizer.summary;

import java.util.ArrayList;

public class SummaryInstanceDeployment {

	private int instance;
	private ArrayList<SummaryInstanceDeploymentObject> deployments = new ArrayList<SummaryInstanceDeploymentObject>();

	public int getInstance() {
		return instance;
	}
	public void setInstance(int instance) {
		this.instance = instance;
	}
	public ArrayList<SummaryInstanceDeploymentObject> getDeployments() {
		return deployments;
	}
	public void setDeployments(
			ArrayList<SummaryInstanceDeploymentObject> deployments) {
		this.deployments = deployments;
	}
}

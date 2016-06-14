package com.cisco.acisizer.summary;

public class SummaryElements {

	private SummaryUtilization utilization = new  SummaryUtilization();
	private SummaryDeployment deployment = new SummaryDeployment();
	
	public SummaryDeployment getDeployment() {
		return deployment;
	}

	public void setDeployment(SummaryDeployment deployment) {
		this.deployment = deployment;
	}

	public SummaryUtilization getUtilization() {
		return utilization;
	}

	public void setUtilization(SummaryUtilization utilization) {
		this.utilization = utilization;
	}
}

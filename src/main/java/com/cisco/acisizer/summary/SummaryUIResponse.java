package com.cisco.acisizer.summary;

import java.util.ArrayList;

public class SummaryUIResponse {

	ArrayList<SummaryUIResource> resourceGridData = new ArrayList<SummaryUIResource>();

	public ArrayList<SummaryUIResource> getResourceGridData() {
		return resourceGridData;
	}

	public void setResourceGridData(ArrayList<SummaryUIResource> resourceGridData) {
		this.resourceGridData = resourceGridData;
	}
}

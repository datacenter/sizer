package com.cisco.acisizer.physical.rest.models;

import java.util.List;

import com.cisco.acisizer.domain.PortGroup;

public class ServerUi {
	private int templateTypeId;
	private int numOfInstances;
	private List<PortGroup> portGroups;
	private boolean isUcsManaged;

	
	/**
	 * @return the numOfInstances
	 */
	public int getNumOfInstances() {
		return numOfInstances;
	}

	/**
	 * @param numOfInstances
	 *            the numOfInstances to set
	 */
	public void setNumOfInstances(int numOfInstances) {
		this.numOfInstances = numOfInstances;
	}

	/**
	 * @return the isUcsManaged
	 */
	public boolean isUcsManaged() {
		return isUcsManaged;
	}

	/**
	 * @param isUcsManaged
	 *            the isUcsManaged to set
	 */
	public void setUcsManaged(boolean isUcsManaged) {
		this.isUcsManaged = isUcsManaged;
	}

	/**
	 * @return the templateTypeId
	 */
	public int getTemplateTypeId() {
		return templateTypeId;
	}

	/**
	 * @param templateTypeId the templateTypeId to set
	 */
	public void setTemplateTypeId(int templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	/**
	 * @return the portGroups
	 */
	public List<PortGroup> getPortGroups() {
		return portGroups;
	}

	/**
	 * @param portGroups the portGroups to set
	 */
	public void setPortGroups(List<PortGroup> portGroups) {
		this.portGroups = portGroups;
	}

}

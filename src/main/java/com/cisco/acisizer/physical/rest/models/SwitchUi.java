package com.cisco.acisizer.physical.rest.models;

import java.util.List;

import com.cisco.acisizer.domain.PortGroup;

public class SwitchUi {
	private int templateTypeId;
	private List<PortGroup> portGroups;
	private String placementPattern;

	/**
	 * @return the templateTypeId
	 */
	public int getTemplateTypeId() {
		return templateTypeId;
	}

	/**
	 * @param templateTypeId
	 *            the templateTypeId to set
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
	 * @param portGroups
	 *            the portGroups to set
	 */
	public void setPortGroups(List<PortGroup> portGroups) {
		this.portGroups = portGroups;
	}

	/**
	 * @return the placementPattern
	 */
	public String getPlacementPattern() {
		return placementPattern;
	}

	/**
	 * @param placementPattern
	 *            the placementPattern to set
	 */
	public void setPlacementPattern(String placementPattern) {
		this.placementPattern = placementPattern;
	}

}

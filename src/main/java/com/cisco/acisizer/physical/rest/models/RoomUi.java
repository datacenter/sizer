/**
 * 
 */
package com.cisco.acisizer.physical.rest.models;

/**
 * @author Mahesh
 *
 */
public class RoomUi {

	private int noOfRows;
	private int noOfRacks;
	private int rackTypeId;
	private int policyId;


	/**
	 * @return the noOfRows
	 */
	public int getNoOfRows() {
		return noOfRows;
	}

	/**
	 * @param noOfRows
	 *            the noOfRows to set
	 */
	public void setNoOfRows(int noOfRows) {
		this.noOfRows = noOfRows;
	}

	/**
	 * @return the noOfRacks
	 */
	public int getNoOfRacks() {
		return noOfRacks;
	}

	/**
	 * @param noOfRacks
	 *            the noOfRacks to set
	 */
	public void setNoOfRacks(int noOfRacks) {
		this.noOfRacks = noOfRacks;
	}

	/**
	 * @return the rackTypeId
	 */
	public int getRackTypeId() {
		return rackTypeId;
	}

	/**
	 * @param rackTypeId the rackTypeId to set
	 */
	public void setRackTypeId(int rackTypeId) {
		this.rackTypeId = rackTypeId;
	}

	/**
	 * @return the policyId
	 */
	public int getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
}

/**
 * 
 */
package com.cisco.acisizer.physical.rest.models;

import java.util.List;

/**
 * @author Mahesh
 *
 */
public class RoomTerminationUi {
	private SwitchUi switchUi;
	private int preferredSpineId;
	private List<Integer> rowIds;
	private SpineRack spineRack;
	private boolean terminateRow;
	private boolean addLeaf;
	private boolean terminateSpine;
	private boolean useNetworkRack;

	
	public boolean isTerminateRow() {
		return terminateRow;
	}

	public void setTerminateRow(boolean terminateRow) {
		this.terminateRow = terminateRow;
	}

	public boolean isAddLeaf() {
		return addLeaf;
	}

	public void setAddLeaf(boolean addLeaf) {
		this.addLeaf = addLeaf;
	}

	public boolean isTerminateSpine() {
		return terminateSpine;
	}

	public void setTerminateSpine(boolean terminateSpine) {
		this.terminateSpine = terminateSpine;
	}

	public boolean isUseNetworkRack() {
		return useNetworkRack;
	}

	public void setUseNetworkRack(boolean useNetworkRack) {
		this.useNetworkRack = useNetworkRack;
	}

	public int getPreferredSpineId() {
		return preferredSpineId;
	}

	public void setPreferredSpineId(int preferredSpineId) {
		this.preferredSpineId = preferredSpineId;
	}

	public List<Integer> getRowIds() {
		return rowIds;
	}

	public void setRowIds(List<Integer> rowIds) {
		this.rowIds = rowIds;
	}

	public SpineRack getSpineRack() {
		return spineRack;
	}

	public void setSpineRack(SpineRack spineRack) {
		this.spineRack = spineRack;
	}

	/**
	 * @return the switchUi
	 */
	public SwitchUi getSwitchUi() {
		return switchUi;
	}

	/**
	 * @param switchUi the switchUi to set
	 */
	public void setSwitchUi(SwitchUi switchUi) {
		this.switchUi = switchUi;
	}
}

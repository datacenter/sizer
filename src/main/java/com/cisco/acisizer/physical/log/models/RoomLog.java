/**
 * 
 */
package com.cisco.acisizer.physical.log.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;

/**
 * @author Mahesh
 *
 */
public class RoomLog {
	private long addRowStartTime;
	private long addRowStopTime;
	private long addRowDiffTime;

	private long calculateInventoryStartTime;
	private long calculateInventoryStopTime;
	private long calculateInventoryDiffTime;

	private long roomSaveStartTime;
	private long roomSaveEndTime;
	private long roomSaveDiffTime;

	/**
	 * @return the roomSaveStartTime
	 */
	public long getRoomSaveStartTime() {
		return roomSaveStartTime;
	}

	/**
	 * @param roomSaveStartTime
	 *            the roomSaveStartTime to set
	 */
	public void setRoomSaveStartTime(long roomSaveStartTime) {
		this.roomSaveStartTime = roomSaveStartTime;
	}

	/**
	 * @return the roomSaveEndTime
	 */
	public long getRoomSaveEndTime() {
		return roomSaveEndTime;
	}

	/**
	 * @param roomSaveEndTime
	 *            the roomSaveEndTime to set
	 */
	public void setRoomSaveEndTime(long roomSaveEndTime) {
		this.roomSaveEndTime = roomSaveEndTime;
	}

	/**
	 * @return the roomSaveDiffTime
	 */
	public long getRoomSaveDiffTime() {
		return roomSaveDiffTime;
	}

	/**
	 * @param roomSaveDiffTime
	 *            the roomSaveDiffTime to set
	 */
	public void setRoomSaveDiffTime(long roomSaveDiffTime) {
		this.roomSaveDiffTime = roomSaveDiffTime;
	}

	public long getAddRowStartTime() {
		return addRowStartTime;
	}

	public void setAddRowStartTime(long addRowStartTime) {
		this.addRowStartTime = addRowStartTime;
	}

	public long getAddRowStopTime() {
		return addRowStopTime;
	}

	public void setAddRowStopTime(long addRowStopTime) {
		this.addRowStopTime = addRowStopTime;
	}

	public long getAddRowDiffTime() {
		return addRowDiffTime;
	}

	public void setAddRowDiffTime(long addRowDiffTime) {
		this.addRowDiffTime = addRowDiffTime;
	}

	public long getCalculateInventoryStartTime() {
		return calculateInventoryStartTime;
	}

	public void setCalculateInventoryStartTime(long calculateInventoryStartTime) {
		this.calculateInventoryStartTime = calculateInventoryStartTime;
	}

	public long getCalculateInventoryStopTime() {
		return calculateInventoryStopTime;
	}

	public void setCalculateInventoryStopTime(long calculateInventoryStopTime) {
		this.calculateInventoryStopTime = calculateInventoryStopTime;
	}

	public long getCalculateInventoryDiffTime() {
		return calculateInventoryDiffTime;
	}

	public void setCalculateInventoryDiffTime(long calculateInventoryDiffTime) {
		this.calculateInventoryDiffTime = calculateInventoryDiffTime;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(AciPhysicalSizerConstants.DATE_FORMAT_HH_MM_SS);
		this.addRowDiffTime = this.addRowStopTime - this.addRowStartTime;
		this.calculateInventoryDiffTime = this.calculateInventoryStopTime - this.calculateInventoryStartTime;
		this.roomSaveDiffTime = this.roomSaveEndTime - this.roomSaveStartTime;

		return "" + sdf.format(new Date(this.addRowStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.addRowStopTime)) + AciPhysicalSizerConstants.SEPERATOR + this.addRowDiffTime
				+ AciPhysicalSizerConstants.SEPERATOR + sdf.format(new Date(this.calculateInventoryStartTime))
				+ AciPhysicalSizerConstants.SEPERATOR + sdf.format(new Date(this.calculateInventoryStopTime))
				+ AciPhysicalSizerConstants.SEPERATOR + this.calculateInventoryDiffTime
				+ AciPhysicalSizerConstants.SEPERATOR + sdf.format(new Date(this.roomSaveStartTime))
				+ AciPhysicalSizerConstants.SEPERATOR + sdf.format(new Date(this.roomSaveEndTime))
				+ AciPhysicalSizerConstants.SEPERATOR + this.roomSaveDiffTime;
	}

}

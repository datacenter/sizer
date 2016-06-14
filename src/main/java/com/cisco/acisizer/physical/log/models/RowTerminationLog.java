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
public class RowTerminationLog {
	private long validationStartTime;
	private long validationEndTime;
	private long validationDiffTime;

	private long autoPlaceSwitchesStartTime;
	private long autoPlaceSwitchesEndTime;
	private long autoPlaceSwitchesDiffTime;

	private long terminationAlgorithmStartTime;
	private long terminationAlgorithmStopTime;
	private long terminationAlgorithmDiffTime;

	private long removeUnUsedSwitchesStartTime;
	private long removeUnUsedSwitchesStopTime;
	private long removeUnUsedSwitchesDiffTime;

	private long calculateInventoryStartTime;
	private long calculateInventoryStopTime;
	private long calculateInventoryDiffTime;

	private long localTerminationStartTime;
	private long localTerminationStopTime;
	private long localTerminationDiffTime;

	private long neighbourTerminationStartTime;
	private long neighbourTerminationStopTime;
	private long neighbourTerminationDiffTime;

	public long getValidationStartTime() {
		return validationStartTime;
	}

	public void setValidationStartTime(long validationStartTime) {
		this.validationStartTime = validationStartTime;
	}

	public long getValidationEndTime() {
		return validationEndTime;
	}

	public void setValidationEndTime(long validationEndTime) {
		this.validationEndTime = validationEndTime;
	}

	public long getValidationDiffTime() {
		return validationDiffTime;
	}

	public void setValidationDiffTime(long validationDiffTime) {
		this.validationDiffTime = validationDiffTime;
	}

	public long getAutoPlaceSwitchesStartTime() {
		return autoPlaceSwitchesStartTime;
	}

	public void setAutoPlaceSwitchesStartTime(long autoPlaceSwitchesStartTime) {
		this.autoPlaceSwitchesStartTime = autoPlaceSwitchesStartTime;
	}

	public long getAutoPlaceSwitchesEndTime() {
		return autoPlaceSwitchesEndTime;
	}

	public void setAutoPlaceSwitchesEndTime(long autoPlaceSwitchesEndTime) {
		this.autoPlaceSwitchesEndTime = autoPlaceSwitchesEndTime;
	}

	public long getAutoPlaceSwitchesDiffTime() {
		return autoPlaceSwitchesDiffTime;
	}

	public void setAutoPlaceSwitchesDiffTime(long autoPlaceSwitchesDiffTime) {
		this.autoPlaceSwitchesDiffTime = autoPlaceSwitchesDiffTime;
	}

	public long getTerminationAlgorithmStartTime() {
		return terminationAlgorithmStartTime;
	}

	public void setTerminationAlgorithmStartTime(long terminationAlgorithmStartTime) {
		this.terminationAlgorithmStartTime = terminationAlgorithmStartTime;
	}

	public long getTerminationAlgorithmStopTime() {
		return terminationAlgorithmStopTime;
	}

	public void setTerminationAlgorithmStopTime(long terminationAlgorithmStopTime) {
		this.terminationAlgorithmStopTime = terminationAlgorithmStopTime;
	}

	public long getTerminationAlgorithmDiffTime() {
		return terminationAlgorithmDiffTime;
	}

	public void setTerminationAlgorithmDiffTime(long terminationAlgorithmDiffTime) {
		this.terminationAlgorithmDiffTime = terminationAlgorithmDiffTime;
	}

	public long getRemoveUnUsedSwitchesStartTime() {
		return removeUnUsedSwitchesStartTime;
	}

	public void setRemoveUnUsedSwitchesStartTime(long removeUnUsedSwitchesStartTime) {
		this.removeUnUsedSwitchesStartTime = removeUnUsedSwitchesStartTime;
	}

	public long getRemoveUnUsedSwitchesStopTime() {
		return removeUnUsedSwitchesStopTime;
	}

	public void setRemoveUnUsedSwitchesStopTime(long removeUnUsedSwitchesStopTime) {
		this.removeUnUsedSwitchesStopTime = removeUnUsedSwitchesStopTime;
	}

	public long getRemoveUnUsedSwitchesDiffTime() {
		return removeUnUsedSwitchesDiffTime;
	}

	public void setRemoveUnUsedSwitchesDiffTime(long removeUnUsedSwitchesDiffTime) {
		this.removeUnUsedSwitchesDiffTime = removeUnUsedSwitchesDiffTime;
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

	public long getLocalTerminationStartTime() {
		return localTerminationStartTime;
	}

	public void setLocalTerminationStartTime(long localTerminationStartTime) {
		this.localTerminationStartTime = localTerminationStartTime;
	}

	public long getLocalTerminationStopTime() {
		return localTerminationStopTime;
	}

	public void setLocalTerminationStopTime(long localTerminationStopTime) {
		this.localTerminationStopTime = localTerminationStopTime;
	}

	public long getLocalTerminationDiffTime() {
		return localTerminationDiffTime;
	}

	public void setLocalTerminationDiffTime(long localTerminationDiffTime) {
		this.localTerminationDiffTime = localTerminationDiffTime;
	}

	public long getNeighbourTerminationStartTime() {
		return neighbourTerminationStartTime;
	}

	public void setNeighbourTerminationStartTime(long neighbourTerminationStartTime) {
		this.neighbourTerminationStartTime = neighbourTerminationStartTime;
	}

	public long getNeighbourTerminationStopTime() {
		return neighbourTerminationStopTime;
	}

	public void setNeighbourTerminationStopTime(long neighbourTerminationStopTime) {
		this.neighbourTerminationStopTime = neighbourTerminationStopTime;
	}

	public long getNeighbourTerminationDiffTime() {
		return neighbourTerminationDiffTime;
	}

	public void setNeighbourTerminationDiffTime(long neighbourTerminationDiffTime) {
		this.neighbourTerminationDiffTime = neighbourTerminationDiffTime;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		this.validationDiffTime = this.validationEndTime - this.validationStartTime;
		this.autoPlaceSwitchesDiffTime = this.autoPlaceSwitchesEndTime - this.autoPlaceSwitchesStartTime;
		this.calculateInventoryDiffTime = this.calculateInventoryStopTime - this.calculateInventoryStartTime;
		this.localTerminationDiffTime = this.localTerminationStopTime - this.localTerminationStartTime;
		this.neighbourTerminationDiffTime = this.neighbourTerminationStopTime - this.neighbourTerminationStartTime;
		this.removeUnUsedSwitchesDiffTime = this.removeUnUsedSwitchesStopTime - this.removeUnUsedSwitchesStartTime;
		this.terminationAlgorithmDiffTime = this.terminationAlgorithmStopTime - this.terminationAlgorithmStartTime;

		return "" + sdf.format(new Date(this.autoPlaceSwitchesStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.autoPlaceSwitchesEndTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.autoPlaceSwitchesDiffTime + AciPhysicalSizerConstants.SEPERATOR
				
				+ sdf.format(new Date(this.calculateInventoryStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.calculateInventoryStopTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.calculateInventoryDiffTime + AciPhysicalSizerConstants.SEPERATOR
				
				+ sdf.format(new Date(this.localTerminationStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.localTerminationStopTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.localTerminationDiffTime + AciPhysicalSizerConstants.SEPERATOR
				
				+ sdf.format(new Date(this.neighbourTerminationStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.neighbourTerminationStopTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.neighbourTerminationDiffTime + AciPhysicalSizerConstants.SEPERATOR
				
				+ sdf.format(new Date(this.removeUnUsedSwitchesStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.removeUnUsedSwitchesStopTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.removeUnUsedSwitchesDiffTime + AciPhysicalSizerConstants.SEPERATOR
				
				+ sdf.format(new Date(this.terminationAlgorithmStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.terminationAlgorithmStopTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.terminationAlgorithmDiffTime + AciPhysicalSizerConstants.SEPERATOR
				
				+ sdf.format(new Date(this.validationStartTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ sdf.format(new Date(this.validationEndTime)) + AciPhysicalSizerConstants.SEPERATOR
				+ this.validationDiffTime;
	}
}

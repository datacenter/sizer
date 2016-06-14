/**
 * 
 */
package com.cisco.acisizer.helper;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.RackTable;
import com.cisco.acisizer.domain.RowTable;

/**
 * @author Mahesh
 *
 */
@Component
public class RowHelper {
	
	@Inject
	private RoomHelper  roomHelper;
	
	@Inject
	private DeviceTypeHelper deviceTypeHelper;
	
	@Inject
	private RackInventoryCalculator rackInventoryCalculator;
	
	/**
	 * @param row
	 */
	public void calculateInventryInfo(RowTable row) {
		InventoryInfo rowInventory = new InventoryInfo();
		deviceTypeHelper.initializeDeviceCounts(rowInventory);
		for (RackTable iter : row.getRacks()) {
			InventoryHelper.addInventory(rowInventory, iter.getInventoryInfo());
		}
		row.setInventoryInfo(rowInventory);
	}
	
	public void calculateRowAndRackInventoryInfo(RowTable row){
		InventoryInfo rowInventory = new InventoryInfo();
		deviceTypeHelper.initializeDeviceCounts(rowInventory);
		for (RackTable iter : row.getRacks()) {
			rackInventoryCalculator.calculateInventryInfo(iter);
			InventoryHelper.addInventory(rowInventory, iter.getInventoryInfo());
		}
		row.setInventoryInfo(rowInventory);
	}
}

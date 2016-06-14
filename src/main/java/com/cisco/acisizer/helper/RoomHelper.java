/**
 * 
 */
package com.cisco.acisizer.helper;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.domain.RowTable;

@Component
public class RoomHelper {

	@Inject
	private RowHelper rowHelper;

	@Inject
	private DeviceTypeHelper deviceTypeHelper;

	/**
	 * @param room
	 */
	public void calculateInventryInfo(RoomTable room) {
		InventoryInfo inventory = new InventoryInfo();
		deviceTypeHelper.initializeDeviceCounts(inventory);
		for (RowTable iter : room.getRows()) {
			InventoryHelper.addInventory(inventory, iter.getInventoryInfo());
		}
		inventory.setTotalNumOfRows(room.getNoOfRows());
		room.setInventoryInfo(inventory);
	}

	public void calculateRoomAndRowInventoryInfo(RoomTable roomTable) {
		for (RowTable rowTable : roomTable.getRows()) {
			rowHelper.calculateInventryInfo(rowTable);
		}
		calculateInventryInfo(roomTable);
	}

	public void calculateRoomRowAndRackInventoryInfo(RoomTable roomTable) {
		InventoryInfo inventory = new InventoryInfo();
		deviceTypeHelper.initializeDeviceCounts(inventory);
		for (RowTable rowTable : roomTable.getRows()) {
			rowHelper.calculateRowAndRackInventoryInfo(rowTable);
			InventoryHelper.addInventory(inventory, rowTable.getInventoryInfo());
		}
		inventory.setTotalNumOfRows(roomTable.getNoOfRows());
		roomTable.setInventoryInfo(inventory);
	}
}

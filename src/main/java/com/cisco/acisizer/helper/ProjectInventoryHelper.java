package com.cisco.acisizer.helper;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.domain.ProjectTable;
import com.cisco.acisizer.domain.RoomTable;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.ProjectsRepository;

@Component
public class ProjectInventoryHelper {

	@Inject
	private DeviceTypeHelper deviceTypeHelper;


	public InventoryInfo calculateAllRoomsInventoryInfo(int projectId, ProjectsRepository projectsRepository)
			throws AciEntityNotFound {
		ProjectTable projectTable = projectsRepository.findOne(projectId);
		InventoryInfo projectInventory = new InventoryInfo();
		deviceTypeHelper.initializeDeviceCounts(projectInventory);
		for (RoomTable iter : projectTable.getRooms()) {

			InventoryHelper.addInventory(projectInventory, iter.getInventoryInfo());
		}

		projectInventory.setTotalNumOfRooms(projectTable.getRooms().size());

		return projectInventory;

	}

}

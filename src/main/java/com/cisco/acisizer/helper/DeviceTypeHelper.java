/**
 * 
 */
package com.cisco.acisizer.helper;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.cisco.acisizer.domain.InventoryInfo;
import com.cisco.acisizer.repo.DeviceTypeRepository;

/**
 * @author Mahesh
 *
 */
@Component
public class DeviceTypeHelper {
	
	@Inject
	private DeviceTypeRepository deviceTypeRepository;
	
	private List<String> types;
	
	@PostConstruct
	public void init(){
		types=deviceTypeRepository.findDevicesByOrder();
	}
	
	/**
	 * @param inventoryInfo
	 */
	public void initializeDeviceCounts(InventoryInfo inventoryInfo) {
		for (String type : types) {
			inventoryInfo.getDeviceCounts().put(type, 0);
		}
	}

}

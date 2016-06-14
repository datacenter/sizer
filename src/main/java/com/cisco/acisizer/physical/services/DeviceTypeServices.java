package com.cisco.acisizer.physical.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.DeviceTypeRepository;
import com.cisco.acisizer.util.ACISizerConstant;

@Service
public class DeviceTypeServices {
	

	@Inject
	private DeviceTypeRepository deviceTypeRepository;

	public List<DeviceType> getDeviceType() {
		List<DeviceType> deviceType = deviceTypeRepository.findAll();
		return deviceType;
	}

	public List<DeviceType> getServers() {
		return deviceTypeRepository.findServers();
	}
	
	public List<DeviceType> getSwitches() {
		return deviceTypeRepository.findDeviceWithType("Leaf");
	}

	public List<DeviceType> getSpines() {
		return deviceTypeRepository.findDeviceWithType("Spine");
	}

	public List<DeviceType> getFirewalls() {
		return deviceTypeRepository.findDeviceWithType("Firewall");
	}
	
	public List<DeviceType> getFabricInterconnects() {
		return deviceTypeRepository.findDeviceWithType("Fabric Interconnect");
	}
	
	public DeviceType addDevice(DeviceType deviceType) {

		switch (deviceType.getType()) {

		case ACISizerConstant.SERVER:
			deviceType.setOrderNo(3);
			break;

		case ACISizerConstant.LEAF:
			deviceType.setOrderNo(1);
			break;

		case ACISizerConstant.SPINE:
			deviceType.setOrderNo(2);
			break;

		case ACISizerConstant.FABRIC_INTERCONNECT:
			deviceType.setOrderNo(4);
			break;

		case ACISizerConstant.FIREWALL:
			deviceType.setOrderNo(5);
			break;
		default:
			deviceType.setOrderNo(6);
			break;

		}
		deviceTypeRepository.save(deviceType);
		return deviceType;
	}
	
	public DeviceType deleteDevice(int id) throws AciEntityNotFound {
		
		DeviceType deviceType = deviceTypeRepository.findOne(id);

		if(null == deviceType ){
			throw new AciEntityNotFound("Cold not find the devicetype");
		}
	
		deviceTypeRepository.delete(id);
		//deviceTypeRepository.save(deviceType);
		return deviceType;
	
		
	}

	public DeviceType updateDevice(int id, DeviceType deviceType) throws AciEntityNotFound {
		if(null == deviceTypeRepository.findOne(id) ){
			throw new AciEntityNotFound("Cold not find the devicetype");
		}
		deviceType.setId(id);
		deviceTypeRepository.save(deviceType);		
		return deviceType;
		
	}
	
	
	
}

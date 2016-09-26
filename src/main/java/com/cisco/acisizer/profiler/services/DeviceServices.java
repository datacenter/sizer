package com.cisco.acisizer.profiler.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.Device;
import com.cisco.acisizer.domain.Model;
import com.cisco.acisizer.domain.Users;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.profiler.rest.controllers.UserController;
import com.cisco.acisizer.repo.DeviceRepository;
import com.cisco.acisizer.repo.ModelRepository;
import com.cisco.acisizer.ui.models.DeviceUi;

@Service
public class DeviceServices {

	@Inject
	private DeviceRepository deviceRepository;
	@Inject
	private ModelRepository modelRepository;

	public Device addDevice(DeviceUi deviceUi) {
		Device device = new Device();
		device.setName(deviceUi.getName());
		device.setIpAddress(deviceUi.getIpAddress());
		device.setUsername(deviceUi.getUsername());
		device.setPassword(deviceUi.getPassword());
		Date date = new Date();
		device.setImportedOn(new Timestamp(date.getTime()));
		device.setImportedStatus(1);
		Model model = modelRepository.findOne(deviceUi.getModelId());
		device.setModelDetails(model);
		if ("aci".equalsIgnoreCase(model.getName())) {
			device.setType("ACI");
		} else {
			device.setType("PauloAlto");
		}
		device.setOwner(deviceUi.getOwner());
		deviceRepository.save(device);
		return device;
	}

	public Device deleteDevice(int id, String userId) throws AciEntityNotFound {
		Device device = deviceRepository.findOne(id);
		if (device.getOwner().equals(userId)) {
			deviceRepository.delete(id);
			return device;
		} else {
			throw new AciEntityNotFound("Can not delete"+" " + device.getOwner() + "'s device");
		}
	}

	public Device updateDevice(int id, DeviceUi deviceUi, String userId) throws AciEntityNotFound {
		Device device = deviceRepository.findOne(id);
		if (device.getOwner().equals(userId)) {
			device.setName(deviceUi.getName());
			device.setIpAddress(deviceUi.getIpAddress());
			device.setUsername(deviceUi.getUsername());
			device.setPassword(deviceUi.getPassword());
			Model model = modelRepository.findOne(deviceUi.getModelId());
			device.setModelDetails(model);
			deviceRepository.save(device);
			return device;
		} else {
			throw new AciEntityNotFound("can not update" +" "+ device.getOwner() + "'s device");
		}
	}

	public Device getDevice(int id, String userId) throws AciEntityNotFound {
		Device device = deviceRepository.findOne(id);
		if (device.getOwner().equals(userId)) {
			return device;
		} else {
			throw new AciEntityNotFound("can not fetch" +" "+ device.getOwner() + "'s device");
		}
	}

	public List<Device> getDevices(String userId) {
		List<Device> device = deviceRepository.findDevicesByUserId(userId);
		return device;
	}

}

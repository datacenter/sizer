package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cisco.acisizer.domain.DeviceType;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.exceptions.GenericInvalidDataException;
import com.cisco.acisizer.models.Application;
import com.cisco.acisizer.physical.services.DeviceTypeServices;
import com.cisco.acisizer.physical.util.AciPhysicalSizerConstants;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(AciPhysicalSizerConstants.DEVICETYPE_URL)

public class DeviceTypeController {

	@Inject
	private DeviceTypeServices deviceTypeServices;


	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DeviceType> getDeviceType() {

		return deviceTypeServices.getDeviceType();
	}
	@ApiOperation(value = "Get the server info", notes = "Get the server info by providing the device type url ", response = Application.class)
	@RequestMapping(value="/servers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DeviceType> getServers() {

		return deviceTypeServices.getServers();
	}
	@ApiOperation(value = "Get the switch info", notes = "Get the switch info by providing the device type url", response = Application.class)
	@RequestMapping(value="/leafs", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DeviceType> getSwitches() {

		return deviceTypeServices.getSwitches();
	}

	@ApiOperation(value = "Get the spine info", notes = "Get the spine info by providing the device type url", response = Application.class)
	@RequestMapping(value="/spines", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DeviceType> getSpines() {

		return deviceTypeServices.getSpines();
	}
	
	@ApiOperation(value = "Get the firewall info", notes = "Get the firewalls info by providing the device type url", response = Application.class)
	@RequestMapping(value="/firewalls", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DeviceType> getFirewalls() {

		return deviceTypeServices.getFirewalls();
	}
	
	@ApiOperation(value = "Get the fabric Interconnects info", notes = "Get the fabric interconnect info by providing the device type url", response = Application.class)
	@RequestMapping(value="/fabricInterconnects", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DeviceType> getFabricInterconnects() {

		return deviceTypeServices.getFabricInterconnects();
	}
	
	
	
	@ApiOperation(value = "Add device", notes = "Add devices by providing device details ", response = Application.class)
	@RequestMapping( method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public DeviceType addDevice(@RequestBody DeviceType deviceType)
	throws AciEntityNotFound, GenericInvalidDataException {

		return deviceTypeServices.addDevice(deviceType);
	}
	
	@ApiOperation(value = "Delete device", notes = "delete devices by providing device id ", response = Application.class)
	@RequestMapping( value="/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public DeviceType deleteDevice(@PathVariable("id") int id)throws AciEntityNotFound, GenericInvalidDataException {
		
		  return deviceTypeServices.deleteDevice(id);	
	}
	
	@ApiOperation(value = "Update device", notes = "Update device by providing device id ", response = Application.class)
	@RequestMapping( value="/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public DeviceType updateDevice(@PathVariable("id") int id, @RequestBody DeviceType deviceType )throws AciEntityNotFound, GenericInvalidDataException {
		
		  return deviceTypeServices.updateDevice(id, deviceType);	
	}
	
	
}

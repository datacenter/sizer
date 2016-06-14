/**
 * 
 */
package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cisco.acisizer.domain.RackTypeTable;
import com.cisco.acisizer.physical.services.RackTypeServices;

@Controller

public class RackTypeController {

	@Inject
	private RackTypeServices rackTypeServices;

	@RequestMapping(value = "/acisizer/v1/physical/racktypes", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<RackTypeTable> getRackType() {
		return rackTypeServices.getRackType();

	}
}

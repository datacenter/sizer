package com.cisco.acisizer.physical.rest.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.acisizer.physical.services.PolicyServices;
import com.cisco.acisizer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController


public class PolicyController  {
	

	
	@Inject
	private PolicyServices policyServices;

	@JsonView(View.Room.class)
	@RequestMapping(value = "/acisizer/v1/physical/policy", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<com.cisco.acisizer.domain.PolicyTable> getPolicy() {
		return policyServices.getPolicy();

	}
	

	/*@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict, item already exists"),
			@ApiResponse(code = 507, message = "Insufficient Storage, could not save") })

	@ApiOperation(value = "Add a Policy ", notes = "Only single Policy can be added with the call", response = Application.class, responseContainer = "Self")
	@RequestMapping(value = "/acisizer/v1/policy", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Policy addPolicy( @Valid @RequestBody Policy policy)	throws AciEntityNotFound, ForbiddenOperationException {
		return policy;
	}
	@ApiResponses(value = { @ApiResponse(code = 507, message = "Insufficient Storage, could not save") })

	@ApiOperation(value = "Delete the policy", notes = "Delete the policy by providing policy id")
	@RequestMapping(value = "/acisizer/v1/policy/{policyId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public int deletePolicy(@PathVariable("policyId") int policyId) throws AciEntityNotFound {
		return 0;
	}
	
	@ApiOperation(value = "Get the Policy info", notes = "Get the Policy info by providing policy id", response = Application.class)
	@RequestMapping(value = "/acisizer/v1/policy/{policyId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Policy getPolicy(@PathVariable("policyId") int policyId) throws AciEntityNotFound {
		return null;
	}*/
	
	
	
	
}

	




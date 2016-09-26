/**
 * 
 */
package com.cisco.acisizer.profiler.rest.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cisco.acisizer.domain.Users;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.models.UserResponse;
import com.cisco.acisizer.profiler.services.UserServices;
import com.cisco.acisizer.repo.UserRepository;
import com.cisco.acisizer.security.util.JWTUtil;
import com.cisco.acisizer.ui.models.UserUi;
import com.cisco.acisizer.util.ApplicationProfilerConstants;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Admin
 *
 */
@Controller
public class UserController {

	@Inject
	private JWTUtil jwtUtil;

	@Inject
	private UserRepository userRepository;

	@Inject
	private Gson gson;

	@Inject
	public UserServices userServices;

	@RequestMapping(value = ApplicationProfilerConstants.LOGIN, method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	private UserResponse login(@RequestBody UserUi userUi) throws AciEntityNotFound {
		com.cisco.acisizer.domain.Users users = userRepository.findUserByUserName(userUi.getUsername());
		if (!users.getPassword().equals(userUi.getPassword())) {
			throw new AciEntityNotFound();
		}
		System.out.println("Logged in User is : "+ users.getUsername());
		UserResponse userResponse = outputMapping(users);
		userResponse.setJwtKey(jwtUtil.createJWT("" + Math.random(), gson.toJson(userResponse)));
		return userResponse;

	}

	private UserResponse outputMapping(Users users) {
		UserResponse userResponse = new UserResponse();
		userResponse.setRole(users.getRole());
		userResponse.setUserId(users.getId());
		userResponse.setUsername(users.getUsername());
		return userResponse;
	}
	
	

	@ApiOperation(value = "Add a user", notes = "Only single user can be added with the call", response = UserUi.class, responseContainer = "Self")
	@RequestMapping(value = ApplicationProfilerConstants.ADMIN_URL, method = RequestMethod.POST, produces = ApplicationProfilerConstants.JSON)
	@ResponseBody
	public Users addUser(@RequestBody UserUi userUi) throws AciEntityNotFound, InvalidAttributeValueException {
		return userServices.addUser(userUi);
	}

	@ApiOperation(value = "delete a user", notes = "Only single user can be deleted with the call", response = UserUi.class, responseContainer = "Self")
	@RequestMapping(value = ApplicationProfilerConstants.ADMIN_URL+"/{id}", method = RequestMethod.DELETE, produces = ApplicationProfilerConstants.JSON)
	@ResponseBody
	public Users deleteUser(@PathVariable("id") int Id) throws AciEntityNotFound {
		return userServices.deleteUser(Id);
	}

	@ApiOperation(value = "update a user", notes = "Only single user can be updated with the call", response = UserUi.class, responseContainer = "Self")
	@RequestMapping(value = ApplicationProfilerConstants.ADMIN_URL+"/{id}", method = RequestMethod.PUT, produces = ApplicationProfilerConstants.JSON)
	@ResponseBody
	public Users updateUser(@PathVariable("id") int Id, @RequestBody UserUi userUi) throws AciEntityNotFound {
		return userServices.updateUser(Id, userUi);
	}

	@ApiOperation(value = "fetch a user", notes = "Only single user can be fetched with the call", response = UserUi.class, responseContainer = "Self")
	@RequestMapping(value = ApplicationProfilerConstants.ADMIN_URL+"/{id}", method = RequestMethod.GET, produces = ApplicationProfilerConstants.JSON )
	@ResponseBody
	public Users getUser(@PathVariable("id") int Id) throws AciEntityNotFound {
		return userServices.getUser(Id);
	}

	@ApiOperation(value = "fetch all user", notes = "all user can be fetched with the call", response = UserUi.class, responseContainer = "Self")
	@RequestMapping(value = ApplicationProfilerConstants.ADMIN_URL, method = RequestMethod.GET, produces = ApplicationProfilerConstants.JSON)
	@ResponseBody
	public List<Users> getUsers() throws AciEntityNotFound {
		return userServices.getUsers();
	}
	
	/*@ApiOperation( value = "Get user information", notes = "Only single user information can be fetched with the call")
	@RequestMapping(value = ApplicationProfilerConstants.USER_INFO, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Users getUserInfo(HttpServletRequest request)
			throws AciEntityNotFound {
		return userServices.getUserInfo(request);
	}*/
	
	
}

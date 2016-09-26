package com.cisco.acisizer.profiler.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cisco.acisizer.domain.Users;
import com.cisco.acisizer.exceptions.AciEntityNotFound;
import com.cisco.acisizer.repo.UserRepository;
import com.cisco.acisizer.ui.models.UserUi;

@Service
public class UserServices {

	@Inject
	private UserRepository userRepository;

	public Users addUser(UserUi userUi) throws InvalidAttributeValueException {
		Users users = new Users();
		users.setUsername(userUi.getUsername());
		users.setPassword(userUi.getPassword());
		users.setEmail(userUi.getEmail());
		users.setAuthentication(userUi.getAuthentication());
		users.setRole(userUi.getRole());

		Date date = new Date();
		users.setCreatedTime(new Timestamp(date.getTime()));
		users.setLastUpdatedTime(new Timestamp(date.getTime()));

		userRepository.save(users);
		return users;
	}

	public Users deleteUser(int id) throws AciEntityNotFound {
		Users users = ValidateIdAndGetUser(id);
		userRepository.delete(id);
		return users;
	}

	public Users updateUser(int id, UserUi userUi) throws AciEntityNotFound {
		Users users = ValidateIdAndGetUser(id);
		users.setPassword(userUi.getPassword());
		users.setUsername(userUi.getUsername());
		users.setEmail(userUi.getEmail());
		users.setAuthentication(userUi.getAuthentication());
		users.setRole(userUi.getRole());
		Date date = new Date();
		users.setLastUpdatedTime(new Timestamp(date.getTime()));

		userRepository.save(users);
		return users;

	}

	public Users getUser(int id) throws AciEntityNotFound {
		Users users = ValidateIdAndGetUser(id);
		return users;
	}

	public List<Users> getUsers() throws AciEntityNotFound {
		List<Users> users = userRepository.findAll();
		if (null == users) {
			throw new AciEntityNotFound("No users found");
		}
		return users;
	}

	private Users ValidateIdAndGetUser(int id) throws AciEntityNotFound {
		Users users = userRepository.findOne(id);
		if (null == users) {
			throw new AciEntityNotFound(" user dont exist for the given id");
		}
		return users;
	}

	/*public Users getUserInfo(HttpServletRequest request) {
		String userId = request.getHeader("auth_user");
		if (null == userId) {
			userId = request.getHeader("http_auth_user");
			if (null == userId)
				userId = "admin"; // testing purpose
		}
		Users userExist = userRepository.findUserByUserName(userId);
		if (null == userExist) {
			return addUserToDatabase(userId);
		}
		System.out.println("Logged in user is : " +userId);
		return userExist;
	}

	private Users addUserToDatabase(String userId) {
		Users user = new Users();
		user.setUsername(userId);
		user.setPassword(userId);
		user.setEmail(userId + "@cisco.com");
		user.setAuthentication("Local Authentication");
		user.setRole("ROLE_USER");
		Date date = new Date();
		user.setCreatedTime(new Timestamp(date.getTime()));
		user.setLastUpdatedTime(new Timestamp(date.getTime()));

		userRepository.save(user);
		return user;
	}*/
}

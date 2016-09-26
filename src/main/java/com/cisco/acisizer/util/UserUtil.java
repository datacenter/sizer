package com.cisco.acisizer.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cisco.acisizer.models.UserResponse;

public class UserUtil {

	public static UserResponse getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserResponse response = (UserResponse) auth.getPrincipal();
		return response;
	}
}

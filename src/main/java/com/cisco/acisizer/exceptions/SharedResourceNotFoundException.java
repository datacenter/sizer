package com.cisco.acisizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SharedResourceNotFoundException extends RuntimeException  {
	private static final long serialVersionUID = 100L;
	
	
	public SharedResourceNotFoundException(String msg) {
		super(msg);
	}
}

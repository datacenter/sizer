package com.cisco.acisizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SnapshotNotFoundException extends Exception {
	private static final long serialVersionUID = 100L;
	
	
	public SnapshotNotFoundException(String msg) {
		super(msg);
	}
}

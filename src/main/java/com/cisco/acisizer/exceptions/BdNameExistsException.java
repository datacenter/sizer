package com.cisco.acisizer.exceptions;

//import java.lang.RuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BdNameExistsException extends RuntimeException 
{
	private static final long serialVersionUID = 100L;
	
	
	public BdNameExistsException(String msg) {
		super(msg);
	}

}
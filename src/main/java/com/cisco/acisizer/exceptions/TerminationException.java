/**
 * 
 */
package com.cisco.acisizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mahesh
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TerminationException extends Exception {
	private static final long serialVersionUID = -5013931552475032796L;

	public TerminationException() {
		super("termination failed");
	}

	public TerminationException(String message) {
		super(message);
	}

	public TerminationException(Throwable cause) {
		super(cause);
	}

	public TerminationException(String message, Throwable cause) {
		super(message, cause);
	}
}

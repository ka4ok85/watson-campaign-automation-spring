package com.github.ka4ok85.wca.exceptions;

public class JobBadStateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JobBadStateException(String message) {
		super(message);
	}
}

package com.github.ka4ok85.wca.exceptions;

public class FailedGetAccessTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FailedGetAccessTokenException(String message) {
		super(message);
	}
}

package com.github.ka4ok85.wca.exceptions;

public class BadApiResultException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadApiResultException(String message) {
		super(message);
	}
}
package com.salestock.didik.exception;

public class ValidationErrorException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidationErrorException() {
		super();
	}

	public ValidationErrorException(String message) {
		super(message);
	}

}

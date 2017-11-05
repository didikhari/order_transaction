package com.salestock.didik.exception;

public class RecordNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException() {
		super();
	}
	
	public RecordNotFoundException(String message){
		super(message);
	}
}

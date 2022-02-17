package com.devinhouse.village.exception;

public class NullResidentException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NullResidentException(String message) {
		super(message);
	}
}

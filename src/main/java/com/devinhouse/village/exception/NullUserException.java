package com.devinhouse.village.exception;

public class NullUserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NullUserException(String message) {
		super(message);
	}
}

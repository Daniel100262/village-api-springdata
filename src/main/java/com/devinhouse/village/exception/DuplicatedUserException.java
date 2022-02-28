package com.devinhouse.village.exception;

public class DuplicatedUserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DuplicatedUserException(String message) {
		super(message);
	}
}

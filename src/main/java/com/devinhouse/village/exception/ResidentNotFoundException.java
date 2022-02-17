package com.devinhouse.village.exception;

public class ResidentNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ResidentNotFoundException(String message) {
		super(message);
	}
}

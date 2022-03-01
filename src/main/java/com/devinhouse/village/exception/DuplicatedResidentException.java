package com.devinhouse.village.exception;

public class DuplicatedResidentException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DuplicatedResidentException(String message) {
		super(message);
	}
}

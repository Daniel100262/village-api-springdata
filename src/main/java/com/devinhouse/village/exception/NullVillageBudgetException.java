package com.devinhouse.village.exception;

public class NullVillageBudgetException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NullVillageBudgetException(String message) {
		super(message);
	}
}

package com.devinhouse.village.exception;

public class InvalidCredentialPropertyException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidCredentialPropertyException(String perperty) {
		super("A propriedade \""+perperty+"\"está nula ou não foi preenchida corretamente!");
	}
}

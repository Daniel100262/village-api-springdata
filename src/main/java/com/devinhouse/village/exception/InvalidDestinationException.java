package com.devinhouse.village.exception;

public class InvalidDestinationException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidDestinationException(String address) {
		super("O endereço "+address+" recebido é inválido!");
	}
}

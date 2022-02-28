package com.devinhouse.village.exception;

public class NullDTOException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NullDTOException(String dtoName) {
		super("O DTO "+dtoName+" recebido está nulo ou inválido!");
	}
}

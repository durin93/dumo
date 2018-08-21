package com.durin.security;

public class ExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ExistException() {
		super();
	}
	
	public ExistException(String message) {
		super(message);
	}
	
}

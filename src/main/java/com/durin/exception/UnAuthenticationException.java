package com.durin.exception;

public class UnAuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnAuthenticationException() {
        super();
    }
    
    public UnAuthenticationException(String message) {
        super(message);
    }
}

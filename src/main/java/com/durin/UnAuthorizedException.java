package com.durin;

public class UnAuthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
        super();
    }
  
    public UnAuthorizedException(String message) {
        super(message);
    }

}

package com.durin.domain;

import com.durin.validate.Validator;


public class Result {
	public static final String MAIN_PAGE = "/memos";
	public static final String LINK_PAGE = "/links";
	public static final String JOIN_PAGE = "/users/join";
	public static final String ERROR_ID = "id";
	public static final String ERROR_PASSWORD = "password";
	
	
	private boolean valid;
	private String errorPart;
	private String errorMessage;
	private String url;
	
	public Result() {
	}

	public Result(boolean valid, String errorMessage, String url) {
		this.valid = valid;
		this.errorMessage = errorMessage;
		this.url = url;
	}
	
	public Result(boolean valid, String errorMessage, String url, String errorPart) {
		this.valid = valid;
		this.errorMessage = errorMessage;
		this.url = url;
		this.errorPart = errorPart;
	}

	public static Result success(String url) {
		return new Result(true, null, url);
	}
	
	public static Result fail(String message, String errorpart) {
		return new Result(false, message, null, errorpart);
	}
	

	public static Result fail(Validator validator) {
		if(validator.getIdMsg()!="") {
			return new Result(false,validator.getIdMsg(),null,"id");
		}
		return new Result(false,validator.getPasswordMsg(),null,"password");
	}


	public boolean isValid() {
		return valid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getUrl() {
		return url;
	}

	public String getErrorPart() {
		return errorPart;
	}

	@Override
	public String toString() {
		return "Result [valid=" + valid + ", errorPart=" + errorPart + ", errorMessage=" + errorMessage + ", url=" + url
				+ "]";
	}



}

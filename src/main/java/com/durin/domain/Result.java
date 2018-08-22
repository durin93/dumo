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
		this(valid,errorMessage,url);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((errorPart == null) ? 0 : errorPart.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + (valid ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (errorPart == null) {
			if (other.errorPart != null)
				return false;
		} else if (!errorPart.equals(other.errorPart))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (valid != other.valid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Result [valid=" + valid + ", errorPart=" + errorPart + ", errorMessage=" + errorMessage + ", url=" + url
				+ "]";
	}



}

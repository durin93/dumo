package com.durin.domain;

public class Result {
	private static final String MAIN_PAGE = "/memos";
	private boolean valid;
	private String errorPart;
	private String errorMessage;
	private String url;
	
	
	public Result() {
	}

	public Result(boolean valid, String errorMessage) {
		this.valid = valid;
		this.errorMessage = errorMessage;
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
	
	public static Result success() {
		return new Result(true, null, MAIN_PAGE);
	}
	
	public static Result failById(String message) {
		return new Result(false, message, null, "id");
	}
	

	public static Result faildByPassword(String message) {
		return new Result(false, message, null, "password" );
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



}

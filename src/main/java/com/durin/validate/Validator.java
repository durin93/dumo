package com.durin.validate;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Validator {

	private String idMsg;
	private String passwordMsg;
	
	
	public Validator(String idMsg, String passwordMsg) {
		this.idMsg = idMsg;
		this.passwordMsg = passwordMsg;
	}
	
	
	public static Validator of(BindingResult bindingResult) {
		return new Validator(check(bindingResult.getFieldError("userId")),check(bindingResult.getFieldError("password")));
	}
	
	public static String check(FieldError msg) {
		if(msg==null) {
			return "";
		}
		return msg.getDefaultMessage();
	}


	public String getIdMsg() {
		return idMsg;
	}


	public String getPasswordMsg() {
		return passwordMsg;
	}


	@Override
	public String toString() {
		return "Validator [idMsg=" + idMsg + ", passwordMsg=" + passwordMsg + "]";
	}





	
}

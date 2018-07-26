//package com.durin.security;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class Bcrypt {
//
//	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//	
//	
//	public static String encrypt(String password) {
//		return  encoder.encode(password);
//	}
//	
//	public static Boolean isMatch(String password, String encodedPassword) {
//		return encoder.matches(password, encodedPassword);
//	}
//	
//}

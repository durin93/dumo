package com.durin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encrpytion {

	private static final Logger log = LoggerFactory.getLogger(Encrpytion.class);

	private static PasswordEncoder passwordEncoder;

	public Encrpytion() {
	}

	public Encrpytion(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	
	public static String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public static Boolean match(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}

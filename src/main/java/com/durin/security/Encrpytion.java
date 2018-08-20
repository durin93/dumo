package com.durin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encrpytion {

	private static final Logger log = LoggerFactory.getLogger(Encrpytion.class);

	private static PasswordEncoder passwordEncoder;

	public Encrpytion() {
	}

	public Encrpytion(PasswordEncoder passwordEncoder) {
		log.debug("Encryption 생성자");
		Encrpytion.passwordEncoder = passwordEncoder;
	}

	
	public static String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public static Boolean match(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}

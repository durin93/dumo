package com.durin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ModuDumoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuDumoApplication.class, args);
	}
}

package com.jiho.anniehands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AnniehandsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnniehandsApplication.class, args);
	}

}

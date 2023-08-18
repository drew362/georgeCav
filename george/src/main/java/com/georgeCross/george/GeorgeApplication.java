package com.georgeCross.george;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class GeorgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeorgeApplication.class, args);
	}

}

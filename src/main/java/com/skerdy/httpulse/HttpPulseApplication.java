package com.skerdy.httpulse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HttpPulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpPulseApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("Spring Shell is running...");
		};
	}

}

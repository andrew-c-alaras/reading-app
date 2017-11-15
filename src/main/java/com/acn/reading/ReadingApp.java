package com.acn.reading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class ReadingApp {

	public static void main(String[] args) {
		SpringApplication.run(ReadingApp.class, args);
	}

}

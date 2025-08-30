package com.example.spring_telecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringTelecomApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringTelecomApplication.class, args);
	}
}

@RestController
class HelloController {
	@GetMapping("/api/hello")
	public String hello() {
		return "Hello from Spring Telecom!";
	}

	@GetMapping("/")
	public String root() {
		return "Welcome to the Spring Telecom API!";
	}
}
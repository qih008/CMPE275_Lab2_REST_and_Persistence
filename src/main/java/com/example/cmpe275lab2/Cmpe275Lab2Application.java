package com.example.cmpe275lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Cmpe275Lab2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cmpe275Lab2Application.class, args);
	}
}

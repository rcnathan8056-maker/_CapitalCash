package com.example.CapitalCash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@EntityScan(basePackages = "java")
@SpringBootApplication
public class CapitalCashApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapitalCashApplication.class, args);
	}

}

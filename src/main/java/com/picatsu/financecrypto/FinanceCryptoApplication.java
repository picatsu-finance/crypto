package com.picatsu.financecrypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
		UserDetailsServiceAutoConfiguration.class})
public class FinanceCryptoApplication {
		// http://localhost:8001/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
	public static void main(String[] args) {
		SpringApplication.run(FinanceCryptoApplication.class, args);
	}

}

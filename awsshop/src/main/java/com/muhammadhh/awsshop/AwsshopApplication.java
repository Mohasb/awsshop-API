package com.muhammadhh.awsshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AwsshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsshopApplication.class, args);
	} 
}

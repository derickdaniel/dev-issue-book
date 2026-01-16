package com.dev.issuebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DevIssueBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevIssueBookApplication.class, args);
	}

}

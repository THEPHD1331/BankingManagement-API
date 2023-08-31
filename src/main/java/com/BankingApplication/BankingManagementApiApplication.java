package com.BankingApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableCaching
@Slf4j
@SpringBootApplication
public class BankingManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagementApiApplication.class, args);
		log.info("Banking management Application has successfully started!");
	}

}

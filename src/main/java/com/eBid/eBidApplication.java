package com.eBid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class eBidApplication {

	public static void main(String[] args) {
		SpringApplication.run(eBidApplication.class, args);
	}

}

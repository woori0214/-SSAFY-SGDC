package com.ssafy.sgdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SgdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgdcApplication.class, args);
	}

}

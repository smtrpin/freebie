package com.youhack.freebie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FreebieApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreebieApplication.class, args);
	}

}

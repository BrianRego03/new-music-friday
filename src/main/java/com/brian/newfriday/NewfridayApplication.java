package com.brian.newfriday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NewfridayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewfridayApplication.class, args);
        System.out.println("Hi");
	}

}

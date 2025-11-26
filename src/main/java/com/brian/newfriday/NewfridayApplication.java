package com.brian.newfriday;

import com.brian.newfriday.service.SpotifyTokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NewfridayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NewfridayApplication.class, args);
        System.out.println("Hi");
        var servicing = context.getBean(SpotifyTokenService.class);
//        System.out.println(servicing.fetchSpotifyToken());
	}

}

package com.brian.newfriday;

import com.brian.newfriday.client.SpotifyClient;
import com.brian.newfriday.service.SpotifyTokenService;
import com.brian.newfriday.service.UserService;
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
        var altService = context.getBean(SpotifyClient.class);
//        altService.getArtist("1oPRcJUkloHaRLYx0olBLJ");
//        altService.getArtistInclusive("1oPRcJUkloHaRLYx0olBLJ");
//        altService.getArtistInclusive("2gsggkzM5R49q6jpPvazou");
//        altService.searchArtist("magdalena bay");
        var userServicer = context.getBean(UserService.class);
        userServicer.registerUser("Tina","tina1717@gmail.com","funnystuff");
	}

}

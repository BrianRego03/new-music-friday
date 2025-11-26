package com.brian.newfriday.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SpotifyTokenService {
    private String spotifyToken;

    @Value("${app.spotify.clientBearer}")
    private String clientBearer;

    public SpotifyTokenService(){

    }

    public void fetchSpotifyToken(){
        RestClient spotifyClient = RestClient.create();
        JsonNode jsonResponse = spotifyClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .headers(headers->{
                    headers.set("Authorization",clientBearer);
                    headers.set("Content-Type","application/x-www-form-urlencoded");
                })
                .body("grant_type=client_credentials")
                .retrieve()
                .body(JsonNode.class);

        System.out.println(jsonResponse);
    }
}

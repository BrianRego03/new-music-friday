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

    private final RestClient restClient;

    public SpotifyTokenService(RestClient restClient){
        this.restClient=restClient;
    }

    public String fetchSpotifyToken(){

        JsonNode jsonResponse = restClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .headers(headers->{
                    headers.set("Authorization",clientBearer);
                    headers.set("Content-Type","application/x-www-form-urlencoded");
                })
                .body("grant_type=client_credentials")
                .retrieve()
                .body(JsonNode.class);

        System.out.println(jsonResponse);
        if(jsonResponse != null){
            return jsonResponse.get("access_token").asText();
        }else{
            return null;
        }

    }

    public synchronized String getSpotifyToken(){
        if (spotifyToken==null){
            spotifyToken=fetchSpotifyToken();
        }
        return spotifyToken;
    }
}

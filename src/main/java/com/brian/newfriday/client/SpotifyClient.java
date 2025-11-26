package com.brian.newfriday.client;

import com.brian.newfriday.service.SpotifyTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.function.Supplier;

@Service
public class SpotifyClient {
    private final RestClient restClient;
    private final SpotifyTokenService spotifyTokenService;
    private final String baseUrl = "https://api.spotify.com/v1/";

    public SpotifyClient(RestClient restClient,SpotifyTokenService spotifyTokenService){
        this.restClient=restClient;
        this.spotifyTokenService=spotifyTokenService;
    }

    public void getArtist(String id){
        if(id==null){
            return;
        }
        var user = executeWithTokenRetry(()->restClient.get()
                .uri(baseUrl + "artists/" + id)
                .header("Authorization", "Bearer " + spotifyTokenService.getSpotifyToken())
                .retrieve()
                .body(JsonNode.class)

        );

        System.out.println(user.get("name"));
        System.out.println(user.get("href"));
        return;
    }

    private <T> T executeWithTokenRetry(Supplier<T> request){
        try{
            return request.get();
        }catch(HttpClientErrorException e){
            if(e.getStatusCode()== HttpStatus.UNAUTHORIZED){
                spotifyTokenService.refreshToken();
                return request.get();
            }else{
                throw e;
            }
        }
    }


}

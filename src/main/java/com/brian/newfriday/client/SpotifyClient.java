package com.brian.newfriday.client;

import com.brian.newfriday.service.SpotifyTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.function.Supplier;

public class SpotifyClient {
    private final RestClient restClient;
    private final SpotifyTokenService spotifyTokenService;

    public SpotifyClient(RestClient restClient,SpotifyTokenService spotifyTokenService){
        this.restClient=restClient;
        this.spotifyTokenService=spotifyTokenService;
    }

//    public void getUser(Long id){
//
//    }

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

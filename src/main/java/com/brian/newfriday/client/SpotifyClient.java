package com.brian.newfriday.client;

import com.brian.newfriday.service.SpotifyTokenService;
import org.springframework.web.client.RestClient;

public class SpotifyClient {
    private final RestClient restClient;
    private final SpotifyTokenService spotifyTokenService;

    public SpotifyClient(RestClient restClient,SpotifyTokenService spotifyTokenService){
        this.restClient=restClient;
        this.spotifyTokenService=spotifyTokenService;
    }


}

package com.brian.newfriday.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "spotify-api", url = "https://api.spotify.com/v1/")
public interface SpotifyClient {

}

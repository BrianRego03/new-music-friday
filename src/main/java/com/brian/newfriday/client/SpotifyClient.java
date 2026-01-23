package com.brian.newfriday.client;

import com.brian.newfriday.service.SpotifyTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
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

    public void getAlbumsById(String id){
        if(id==null){
            return;
        }
        var user = executeWithTokenRetry(()->restClient.get()
                .uri(baseUrl + "artists/" + id +"/albums?include_groups=album,single&market=US&limit=50")
                .header("Authorization", "Bearer " + spotifyTokenService.getSpotifyToken())
                .retrieve()
                .body(JsonNode.class)

        );
        JsonNode userItems = user.get("items");
        if(userItems!=null && userItems.isArray()){

            ArrayList<JsonNode> itemsList = new ArrayList<>();
            userItems.forEach(itemsList::add);

            itemsList.sort((a,b)->{
                String dateA = a.get("release_date").asText();
                String dateB = b.get("release_date").asText();
                return dateA.compareTo(dateB);
            });

            for(JsonNode item : itemsList){

                System.out.println(item.get("name") + " released on " + item.get("release_date"));
            }
        }

        System.out.println(user.get("name"));
        System.out.println(user.get("href"));
        return;
    }

    public void searchArtist(String searchtext){
        if(searchtext==null){
            return;
        }
        var user = executeWithTokenRetry(()->restClient.get()
                .uri(builder -> builder
                        .scheme("https")
                        .host("api.spotify.com")
                        .path("/v1/search")
                        .queryParam("q",searchtext)
                        .queryParam("type","artist")
                        .build())
                .header("Authorization", "Bearer " + spotifyTokenService.getSpotifyToken())
                .retrieve()
                .body(JsonNode.class)

        );
        JsonNode userArtists = user.get("artists");
        JsonNode userItems = userArtists.get("items");
        if(userItems!=null && userItems.isArray()){

            ArrayList<JsonNode> itemsList = new ArrayList<>();
            userItems.forEach(itemsList::add);

            for(JsonNode item : itemsList){

                System.out.println(item.get("name") + " with popularity " + item.get("popularity"));
            }
        }

//        System.out.println(user.get("name"));
//        System.out.println(user.get("href"));
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

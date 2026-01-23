package com.brian.newfriday.client;

import com.brian.newfriday.dtos.CompleteAlbumDto;
import com.brian.newfriday.dtos.SpotifyAlbumDto;
import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.mappers.AlbumMapper;
import com.brian.newfriday.mappers.ArtistMapper;
import com.brian.newfriday.repository.ArtistRepository;
import com.brian.newfriday.service.SpotifyTokenService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

@Service
public class SpotifyClient {
    private final RestClient restClient;
    private final SpotifyTokenService spotifyTokenService;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;
    private final String baseUrl = "https://api.spotify.com/v1/";

    public SpotifyClient(RestClient restClient,SpotifyTokenService spotifyTokenService,
                         ArtistMapper artistMapper, ArtistRepository artistRepository,AlbumMapper albumMapper){
        this.restClient=restClient;
        this.spotifyTokenService=spotifyTokenService;
        this.artistMapper=artistMapper;
        this.artistRepository=artistRepository;
        this.albumMapper=albumMapper;
    }

    public Artist getArtistFromSpotify(String id){
        if(id==null){
            return null;
        }
        var user = executeWithTokenRetry(()->restClient.get()
                .uri(baseUrl + "artists/" + id)
                .header("Authorization", "Bearer " + spotifyTokenService.getSpotifyToken())
                .retrieve()
                .body(JsonNode.class)

        );

        SpotifyArtistDto artistDto = new SpotifyArtistDto(
                user.get("name").asText(),
                user.get("id").asText(),
                user.get("images").get(2).get("url").asText(),
                user.get("images").get(1).get("url").asText(),
                user.get("images").get(0).get("url").asText()
        );
        Artist artist = artistMapper.toArtist(artistDto);
        return artist;

    }

    public CompleteAlbumDto getAlbumsByArtistSpotifyId(String id){
        if(id==null){
            return null;
        }
        var user = executeWithTokenRetry(()->restClient.get()
                .uri(baseUrl + "artists/" + id +"/albums?include_groups=album,single&market=US&limit=50")
                .header("Authorization", "Bearer " + spotifyTokenService.getSpotifyToken())
                .retrieve()
                .body(JsonNode.class)

        );
        if(user==null){
            throw new IllegalStateException("Spotify response was bad");
        }
        JsonNode userItems = user.get("items");

        List<Album> AlbumList = new ArrayList<>();
        List<List<String>> artistIds = new ArrayList<>();
        String currentArtistId="";
        if(userItems!=null && userItems.isArray()){

            List<JsonNode> itemsList = new ArrayList<>();
            userItems.forEach(itemsList::add);

            itemsList.sort(Comparator.comparing(
                    a -> LocalDate.parse(a.get("release_date").asText())
            ));
            int minSize = Math.min(itemsList.size(),5);

            for(int i=0;i<minSize;i++){
                JsonNode currentItem = itemsList.get(i);
                SpotifyAlbumDto albumDto = new SpotifyAlbumDto(
                        currentItem.get("name").asText(),
                        currentItem.get("id").asText(),
                        currentItem.get("images").get(2).get("url").asText(),
                        currentItem.get("images").get(1).get("url").asText(),
                        currentItem.get("images").get(0).get("url").asText(),
                        currentItem.get("total_tracks").asInt(),
                        LocalDate.parse(currentItem.get("release_date").asText()),
                        currentItem.get("album_type").asText()
                );
                AlbumList.add(albumMapper.toAlbumEntity(albumDto));
                List<String> artistId = new ArrayList<>();

                for(JsonNode artist : currentItem.get("artists")){
                    if((currentArtistId.isEmpty() &&(currentItem.get("artists").size()==1))){
                        currentArtistId = artist.get("id").asText();
                        break;
                    }
                }
                for(JsonNode artist : currentItem.get("artists")){
                    if(currentItem.get("artists").size()==1){
                        artistId = new ArrayList<>();
                    }else if(!(currentArtistId.equals(artist.get("id").asText()))){
                        artistId.add(artist.get("id").asText());
                    }


                }
                artistIds.add(artistId);
            }
        }


        return new CompleteAlbumDto(AlbumList,artistIds,currentArtistId);
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

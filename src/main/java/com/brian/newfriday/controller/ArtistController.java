package com.brian.newfriday.controller;

import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.mappers.ArtistMapper;
import com.brian.newfriday.repository.ArtistRepository;
import com.brian.newfriday.service.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;
    private final  ArtistMapper artistMapper;

    public ArtistController(ArtistRepository artistRepository, ArtistService artistService,
                            ArtistMapper artistMapper){
        this.artistRepository = artistRepository;
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @GetMapping("/artists/{spotifyId}" )
    public SpotifyArtistDto getArtistBySpotifyId(@PathVariable String spotifyId){
//        Artist artist = artistService.getArtistBySpotifyID(spotifyId);
        Artist artist = artistService.getCompleteArtist(spotifyId);
        return artistMapper.toSpotifyDto(artist);
    }

    @GetMapping("/artists")
    public List<SpotifyArtistDto> getAllArtists(){
        List<Artist> artists = artistService.getAllArtists("name");
        return artists.stream()
                .map(artistMapper::toSpotifyDto)
                .toList();
    }
}

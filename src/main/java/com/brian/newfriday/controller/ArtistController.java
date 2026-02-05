package com.brian.newfriday.controller;

import com.brian.newfriday.client.SpotifyClient;
import com.brian.newfriday.dtos.ArtistResponseDto;
import com.brian.newfriday.dtos.ArtistSearchDto;
import com.brian.newfriday.dtos.SpotifyAlbumDto;
import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.mappers.AlbumMapper;
import com.brian.newfriday.mappers.ArtistMapper;
import com.brian.newfriday.repository.ArtistRepository;
import com.brian.newfriday.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final ArtistService artistService;
    private final  ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;
    private final SpotifyClient spotifyClient;


    public ArtistController(ArtistRepository artistRepository, ArtistService artistService,
                            ArtistMapper artistMapper, AlbumMapper albumMapper, SpotifyClient spotifyClient){
        this.artistRepository = artistRepository;
        this.artistService = artistService;
        this.artistMapper = artistMapper;
        this.albumMapper = albumMapper;
        this.spotifyClient = spotifyClient;
    }

    @GetMapping("/artists/{spotifyId}" )
    public ArtistResponseDto getArtistBySpotifyId(@PathVariable String spotifyId){
//        Artist artist = artistService.getArtistBySpotifyID(spotifyId);
        Artist artist = artistService.getCompleteArtist(spotifyId);
        SpotifyArtistDto artistDto = artistMapper.toSpotifyDto(artist);
        List<SpotifyAlbumDto> albumDtos = artist.getAlbumList()
                .stream().map(albumMapper::toDto).toList();
        ArtistResponseDto responseDto = new ArtistResponseDto(artistDto, albumDtos);
//        return artistMapper.toSpotifyDto(artist);
        return responseDto;
    }

    @GetMapping("/artists")
    public List<SpotifyArtistDto> getAllArtists(){
        List<Artist> artists = artistService.getAllArtists("name");
        return artists.stream()
                .map(artistMapper::toSpotifyDto)
                .toList();
    }

    @GetMapping("/search")
    public List<ArtistSearchDto> searchArtists(@RequestParam("q") String searchText){
        return spotifyClient.searchArtist(searchText);

    }
}

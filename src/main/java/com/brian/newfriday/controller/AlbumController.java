package com.brian.newfriday.controller;

import com.brian.newfriday.dtos.AlbumResponseDto;
import com.brian.newfriday.dtos.SpotifyAlbumDto;
import com.brian.newfriday.dtos.SpotifyArtistDto;
import com.brian.newfriday.entity.Album;
import com.brian.newfriday.entity.Artist;
import com.brian.newfriday.mappers.AlbumMapper;
import com.brian.newfriday.mappers.ArtistMapper;
import com.brian.newfriday.repository.AlbumRepository;
import com.brian.newfriday.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumController {
    private AlbumService albumService;
    private AlbumRepository albumRepository;
    private AlbumMapper albumMapper;
    private ArtistMapper artistMapper;

    public AlbumController(AlbumService albumService, AlbumRepository albumRepository,
                           AlbumMapper albumMapper, ArtistMapper artistMapper){
        this.albumService = albumService;
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.artistMapper = artistMapper;
    }

    @GetMapping("/albums/{SpotifyId}")
    public AlbumResponseDto getAlbumBySpotifyId(@PathVariable String SpotifyId){
        Album album = albumRepository.findBySpotifyID(SpotifyId);
        List<Artist> artists = album.getArtistsAsList();
        SpotifyAlbumDto albumDto = albumMapper.toDto(album);
        List<SpotifyArtistDto> artistDtos = artists.stream()
                .map(artistMapper::toSpotifyDto)
                .toList();
        return new AlbumResponseDto(albumDto, artistDtos);


    }
}

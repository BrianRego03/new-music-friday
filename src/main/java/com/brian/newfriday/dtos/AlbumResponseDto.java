package com.brian.newfriday.dtos;

import java.util.List;

public class AlbumResponseDto {
    private SpotifyAlbumDto album;
    private List<SpotifyArtistDto> artists;
    public AlbumResponseDto(SpotifyAlbumDto album, List<SpotifyArtistDto> artists){
        this.album=album;
        this.artists=artists;
    }
    public AlbumResponseDto(){}
    public SpotifyAlbumDto getAlbum(){
        return this.album;
    }
    public List<SpotifyArtistDto> getArtists(){
        return this.artists;
    }
    public void SetAlbum(SpotifyAlbumDto album){
        this.album=album;
    }
    public void SetArtists(List<SpotifyArtistDto> artists){
        this.artists=artists;
    }
}

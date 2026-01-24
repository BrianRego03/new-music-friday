package com.brian.newfriday.dtos;

import java.util.List;

public class ArtistResponseDto {
    private SpotifyArtistDto artist;
    private List<SpotifyAlbumDto> albums;
    public ArtistResponseDto(SpotifyArtistDto artist, List<SpotifyAlbumDto> albums){
        this.artist=artist;
        this.albums=albums;
    }
    public ArtistResponseDto(){}
    public SpotifyArtistDto getArtist(){
        return this.artist;
    }
    public List<SpotifyAlbumDto> getAlbums(){
        return this.albums;
    }
    public void setArtist(SpotifyArtistDto artist){
        this.artist=artist;
    }
    public void setAlbums(List<SpotifyAlbumDto> albums){
        this.albums=albums;
    }
}

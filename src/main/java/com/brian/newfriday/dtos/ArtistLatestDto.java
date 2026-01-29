package com.brian.newfriday.dtos;


public class ArtistLatestDto {
    private String artistId;
    private String latestAlbumId;

    public ArtistLatestDto(String artistId, String latestAlbumId){
        this.artistId = artistId;
        this.latestAlbumId = latestAlbumId;
    }
    public String getArtistId(){
        return this.artistId;
    }
    public String getLatestAlbumId(){
        return this.latestAlbumId;
    }


}

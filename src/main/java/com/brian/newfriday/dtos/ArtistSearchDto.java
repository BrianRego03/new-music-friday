package com.brian.newfriday.dtos;

public class ArtistSearchDto {
    private String name;
    private String spotifyId;
    private String image;

    public ArtistSearchDto(String name,String spotifyId,String image){
        this.name=name;
        this.image=image;
        this.spotifyId=spotifyId;
    }

    public ArtistSearchDto(){}

    public String getName(){
        return this.name;
    }

    public String getSpotifyId(){
        return this.spotifyId;
    }

    public String getImage(){
        return this.image;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSpotifyId(String spotifyId){
        this.spotifyId = spotifyId;
    }

    public void setImage(String image){
        this.image = image;
    }


}

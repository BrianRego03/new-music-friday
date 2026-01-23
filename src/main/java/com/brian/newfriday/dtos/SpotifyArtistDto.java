package com.brian.newfriday.dtos;

public class SpotifyArtistDto {

    private String name;
    private String spotifyID;
    private String imgSmall;
    private String imgMedium;
    private String imgLarge;
    public SpotifyArtistDto(String name, String spotifyID, String imgSmall, String imgMedium, String imgLarge){
        this.name=name;
        this.spotifyID=spotifyID;
        this.imgSmall=imgSmall;
        this.imgMedium=imgMedium;
        this.imgLarge=imgLarge;
    }

    public SpotifyArtistDto(){}

    public String getName(){
        return this.name;
    }
    public String getSpotifyID(){
        return this.spotifyID;
    }
    public String getImgSmall(){
        return this.imgSmall;
    }
    public String getImgMedium(){
        return this.imgMedium;
    }
    public String getImgLarge(){
        return this.imgLarge;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setSpotifyID(String spotifyID){
        this.spotifyID=spotifyID;
    }
    public void setImgSmall(String imgSmall){
        this.imgSmall=imgSmall;
    }
    public void setImgMedium(String imgMedium){
        this.imgMedium=imgMedium;
    }
    public void setImgLarge(String imgLarge){
        this.imgLarge=imgLarge;
    }
}

package com.brian.newfriday.dtos;

import java.time.LocalDate;

public class SpotifyAlbumDto {

    private String name;
    private String spotifyID;
    private String imgSmall;
    private String imgMedium;
    private String imgLarge;
    private int trackLength;
    private LocalDate releaseDate;
    private String albumType;
    public SpotifyAlbumDto(String name, String spotifyID, String imgSmall, String imgMedium, String imgLarge,
                           int trackLength, LocalDate releaseDate, String albumType){
        this.name=name;
        this.spotifyID=spotifyID;
        this.imgSmall=imgSmall;
        this.imgMedium=imgMedium;
        this.imgLarge=imgLarge;
        this.trackLength=trackLength;
        this.releaseDate=releaseDate;
        this.albumType=albumType;
    }

    public SpotifyAlbumDto(){}

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

    public int getTrackLength(){
        return this.trackLength;
    }

    public LocalDate getReleaseDate(){
        return this.releaseDate;
    }

    public String getAlbumType(){
        return this.albumType;
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

    public void setTrackLength(int trackLength){
        this.trackLength=trackLength;
    }

    public void setReleaseDate(LocalDate releaseDate){
        this.releaseDate=releaseDate;
    }

    public void setAlbumType(String albumType){
        this.albumType=albumType;
    }
}

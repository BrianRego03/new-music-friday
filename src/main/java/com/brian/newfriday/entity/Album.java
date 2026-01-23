package com.brian.newfriday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "spotify_id")
    private String spotifyID;

    @Column(name = "img_small")
    private String imgSmall;

    @Column(name = "img_medium")
    private String imgMedium;

    @Column(name= "img_large")
    private String imgLarge;

    @Column(name = "track_length")
    private int trackLength;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "album_type")
    private Record albumType;

    @Column(name = "create_time",updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDate createTime;

    @ManyToMany(mappedBy = "albumSet")
    private Set<Artist> artistSet = new HashSet<>();

    public Album(String name, String spotifyID, String imgSmall, String imgMedium,
                 String imgLarge, int trackLength, LocalDate releaseDate, Record albumType){
        this.name=name;
        this.spotifyID=spotifyID;
        this.imgSmall=imgSmall;
        this.imgMedium=imgMedium;
        this.imgLarge=imgLarge;
        this.trackLength=trackLength;
        this.releaseDate=releaseDate;
        this.albumType=albumType;
        this.createTime=LocalDate.now();
    }

    public Album(){

    }

    public String getName() {
        return name;
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public String getImgMedium() {
        return imgMedium;
    }

    public String getImgLarge() {
        return imgLarge;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Record getAlbumType() {
        return albumType;
    }

    public int getId() {
        return id;
    }

    public Set<Artist> getArtistSet() {
        return artistSet;
    }

    public List<Artist> getArtistsAsList() {
        return artistSet.stream().toList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public void setImgMedium(String imgMedium) {
        this.imgMedium = imgMedium;
    }

    public void setImgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
    }

    public void setAlbumType(Record albumType) {
        this.albumType = albumType;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    public void addArtist(Artist artist){
        this.artistSet.add(artist);
        artist.getAlbumSet().add(this);
    }

    public void removeArtist(Artist artist){
        this.artistSet.remove(artist);
        artist.getAlbumSet().remove(this);
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", spotifyID='" + spotifyID + '\'' +
                ", trackLength=" + trackLength +
                ", releaseDate=" + releaseDate +
                ", albumType=" + albumType +
                '}';
    }
}

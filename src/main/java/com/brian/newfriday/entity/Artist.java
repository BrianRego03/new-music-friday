package com.brian.newfriday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="spotify_id")
    private String spotifyID;

    @Column(name="img_small")
    private String imgSmall;

    @Column(name="img_medium")
    private String imgMedium;

    @Column(name="img_large")
    private String imgLarge;

    @Column(name="create_time",updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDate createTime;

    @ManyToMany
    @JoinTable(
            name = "artist_albums",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private Set<Album> albumSet = new HashSet<>();

    public Artist(){}

    public Artist(String name, String spotifyID, String imgSmall, String imgMedium, String imgLarge){
        this.name=name;
        this.spotifyID=spotifyID;
        this.imgSmall=imgSmall;
        this.imgMedium=imgMedium;
        this.imgLarge=imgLarge;
        this.createTime=LocalDate.now();

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    @Override
    public String toString(){
        return name + " - " + spotifyID;
    }
}

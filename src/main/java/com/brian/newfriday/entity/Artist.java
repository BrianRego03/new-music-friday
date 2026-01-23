package com.brian.newfriday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany(mappedBy="artistSet")
    private Set<User> userSet = new HashSet<>();


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

    public Set<Album> getAlbumSet() {
        return albumSet;
    }

    public List<Album> getAlbumList() {
        return albumSet.stream().toList();
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

    public void addAlbum(Album album){
        this.albumSet.add(album);
        album.getArtistSet().add(this);
    }

    public void removeAlbum(Album album){
        this.albumSet.remove(album);
        album.getArtistSet().remove(this);
    }

    public Set<User> getUserSet(){
        return this.userSet;
    }
    public void addUser(User user){
        this.userSet.add(user);
        user.getArtistSet().add(this);
    }



    @Override
    public String toString(){
        return name + " - " + spotifyID;
    }
}

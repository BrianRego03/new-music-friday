package com.brian.newfriday.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

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





}

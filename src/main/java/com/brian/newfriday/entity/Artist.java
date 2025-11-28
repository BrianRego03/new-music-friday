package com.brian.newfriday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

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

}

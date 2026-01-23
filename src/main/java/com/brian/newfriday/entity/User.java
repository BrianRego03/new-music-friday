package com.brian.newfriday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="create_time",updatable = false,nullable = false)
    @CreationTimestamp
    private LocalDate createTime;

    @ManyToMany
    @JoinTable(
            name="user_artists",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="artist_id")
    )
    private Set<Artist> artistSet = new HashSet<>();

    public User(){}

    public User(String name, String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;
        this.createTime=LocalDate.now();
        this.role=Role.USER;
    }

    public String getName(){return this.name;}

    public void setName(String name){this.name=name;}

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email=email;}

    public String getPassword(){return this.password;}

    public void setPassword(String password){this.password=password;}

    public Role getRole(){return this.role;}

    public void setRole(Role role){this.role=role;}

    public int getId(){
        return this.id;
    }

    public Set<Artist> getArtistSet(){
        return this.artistSet;
    }

    public void AddArtist(Artist artist){
        this.artistSet.add(artist);
        artist.getUserSet().add(this);

    }

    public void removeArtist(Artist artist){
        this.artistSet.remove(artist);
        artist.getUserSet().remove(this);
    }

}

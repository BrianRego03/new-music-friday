package com.brian.newfriday.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

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

    public User(){}

    public User(String name, String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;
        this.createTime=LocalDate.now();
    }

}

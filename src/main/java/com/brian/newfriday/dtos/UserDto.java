package com.brian.newfriday.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    @JsonProperty("user_id")
    private int id;
    private String name;
    private String email;

    public UserDto(int id, String name, String email){
        this.id=id;
        this.name = name;
        this.email = email;
    }

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public String getEmail(){return this.email;}

}

package com.brian.newfriday.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CredentialsRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6,max = 25, message = "Password must be at least 6 characters long")
    private String password;

    public CredentialsRequest(String email, String password){
        this.email=email;
        this.password=password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }
}

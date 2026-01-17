package com.brian.newfriday.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {
    private String secret;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;

    public JwtConfig(){

    }

    public void setSecret(String secret){
        this.secret=secret;
    }

    public void setAccessTokenExpiration(Long accessTokenExpiration){
        this.accessTokenExpiration=accessTokenExpiration;
    }

    public void setRefreshTokenExpiration(Long refreshTokenExpiration){
        this.refreshTokenExpiration=refreshTokenExpiration;
    }

    public String getSecret(){
        return this.secret;
    }

    public long getAccessTokenExpiration(){
        return this.accessTokenExpiration;
    }

    public long getRefreshTokenExpiration(){
        return this.refreshTokenExpiration;
    }

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(getSecret().getBytes());
    }


}

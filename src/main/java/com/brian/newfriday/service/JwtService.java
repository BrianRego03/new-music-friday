package com.brian.newfriday.service;

import com.brian.newfriday.config.JwtConfig;
import com.brian.newfriday.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig){
        this.jwtConfig=jwtConfig;
    }

    public String generateAccessToken(User user){
        final long tokenExpiration = jwtConfig.getAccessTokenExpiration();
        return generateToken(user, tokenExpiration);
    }

    public String generateRefreshToken(User user){
        final long tokenExpiration = jwtConfig.getRefreshTokenExpiration();
        return generateToken(user, tokenExpiration);
    }

    public String generateToken(User user, long tokenExpiration){
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*tokenExpiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();

    }

    public Claims getClaims(String token){
        var claims=Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }
}

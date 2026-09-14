package com.example.linkedInProject.api_gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JWTService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }



//    public String generateRefreshToken(User user){
//        long sixMonths = oneMinute * 60 * 24 * 30 * 6;
//
//        return Jwts.builder()
//                .subject(user.getId().toString())
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis()+ sixMonths))
//                .signWith(getSecretKey())
//                .compact();
//
//    }

    public Long getUserIdFromToken(String token){
        //this takes the encoded header and payload , and passes header, payload, secret key through the algorithm,
        // and produces an encoded control signature, if it doesn't match with the token provided, authentication is failed

        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());

    }
}




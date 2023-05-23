package com.example.springboot6.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    @Value("${app.secret}")
    private String secret;

  //Generation of Token
    public String generateToken(String subject){

        return Jwts.builder().setSubject(subject).setIssuer(
                "RupeshDjango"
        ).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256,secret.getBytes()).compact();
    }


    //Reading Claims
    public Claims getClaims(String token){

        return Jwts.parser().setSigningKey(secret.getBytes())
                .parseClaimsJws(token).getBody();
    }

    //Reading Expiry date
    public Date getExpDate(String token){

        return getClaims(token).getExpiration();
    }

    //Reading username
    public String getUsername(String token){

        return getClaims(token).getSubject();
    }


    //validate Exp date
    public boolean isTokenExp(String token){

        Date expDate= getExpDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    //validate username in token and database , expDate
    public boolean validateToken(String token,String username){

        String tokenUsername=getUsername(token);

        return (username.equals(tokenUsername) && !isTokenExp(token));
    }

}

package com.share.jwtsecurity.service;

import com.share.jwtsecurity.config.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    SecurityConfig config;

    public JwtService(SecurityConfig config) {
        this.config = config;
    }

    public String createToken(String username, Date expirationDate) {
        return Jwts.builder()

                .setSubject(username)

                .setExpiration(expirationDate)

                .signWith(SignatureAlgorithm.HS512, config.getJwtSecretKey())

                .compact();

    }

    public Claims getClaims(String token) {
        return Jwts.parser()

                .setSigningKey(config.getJwtSecretKey())

                .parseClaimsJws(token.replace(config.getJwtTokenPrefix(), ""))

                .getBody();
    }
}

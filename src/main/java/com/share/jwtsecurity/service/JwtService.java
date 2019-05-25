package com.share.jwtsecurity.service;

import com.share.jwtsecurity.config.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    ConfigProperties configProperties;

    public JwtService(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    public String createToken(String username, Date expirationDate) {
        return Jwts.builder()

                .setSubject(username)

                .setExpiration(expirationDate)

                .signWith(SignatureAlgorithm.HS512, configProperties.getJwtSecretKey())

                .compact();

    }

    public Claims getClaims(String token) {
        return Jwts.parser()

                .setSigningKey(configProperties.getJwtSecretKey())

                .parseClaimsJws(token.replace(configProperties.getJwtTokenPrefix(), ""))

                .getBody();
    }
}

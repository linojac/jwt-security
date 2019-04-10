package com.share.jwtsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.jwtsecurity.config.SecurityConfig;
import com.share.jwtsecurity.constant.SecurityConstants;
import com.share.jwtsecurity.model.ApplicationUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    SecurityConfig config;

    AuthenticationManager authenticationManager;
    ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper,
                                   SecurityConfig config) {
        super();
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.config = config;
    }

    @Override
    public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws AuthenticationException {
        try {

            ServletInputStream inputStream = request
                    .getInputStream();
            ApplicationUser creds = objectMapper
                    .readValue(inputStream, ApplicationUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        Date expirationDate = new Date(System.currentTimeMillis() + config.getJwtExpirationTime());
        String token = Jwts.builder()

                .setSubject(((User) auth.getPrincipal()).getUsername())

                .setExpiration(expirationDate)

                .signWith(SignatureAlgorithm.HS512, config.getJwtSecretKey())

                .compact();
        res.setHeader(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        res.setHeader(SecurityConstants.EXPIRATION_TIME_MILLIS, String.valueOf(expirationDate.getTime()));
    }
}

package com.share.jwtsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.jwtsecurity.config.ConfigProperties;
import com.share.jwtsecurity.constant.ApplicationConstants;
import com.share.jwtsecurity.exception.ApiError;
import com.share.jwtsecurity.exception.JwtSecurityException;
import com.share.jwtsecurity.service.EncoderService;
import com.share.jwtsecurity.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
import java.util.StringTokenizer;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private ConfigProperties configProperties;

    private AuthenticationManager authenticationManager;

    private ObjectMapper objectMapper;

    private JwtService jwtService;

    private EncoderService encoderService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper,
                                   ConfigProperties configProperties, JwtService jwtService, EncoderService encoderService) {
        super();
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.configProperties = configProperties;
        this.jwtService = jwtService;
        this.encoderService = encoderService;
    }

    @Override
    public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws AuthenticationException {
        try {
            LOGGER.info("Inside attemptAuthentication");

            ServletInputStream inputStream = request
                    .getInputStream();
            String creds = objectMapper
                    .readValue(inputStream, String.class);
            StringTokenizer token = new StringTokenizer(encoderService.decode(creds), ":");

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            token.nextToken(),
                            token.nextToken(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new JwtSecurityException(new ApiError
                    (HttpStatus.FORBIDDEN, "Invalid security token", e));
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Date expirationDate = null;
        if ("true".equalsIgnoreCase(req.getParameter("rememberme"))) {
            expirationDate = new Date(System.currentTimeMillis() + configProperties.getJwtExpirationTime());
        } else {
            expirationDate = new Date(System.currentTimeMillis() + configProperties.getSessionExpirationTime());
        }

        String username = ((User) auth.getPrincipal()).getUsername();
        String token = jwtService.createToken(username, expirationDate);
        res.setHeader(ApplicationConstants.AUTHORIZATION_HEADER, ApplicationConstants.TOKEN_PREFIX + token);
        res.setHeader(ApplicationConstants.EXPIRATION_TIME_MILLIS, String.valueOf(expirationDate.getTime()));
    }
}

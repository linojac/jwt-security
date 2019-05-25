package com.share.jwtsecurity.filter;

import com.share.jwtsecurity.config.ConfigProperties;
import com.share.jwtsecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    ConfigProperties configProperties;
    private JwtService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, ConfigProperties configProperties, JwtService jwtService) {
        super(authenticationManager);
        this.configProperties = configProperties;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        LOGGER.info("Inside authorization filter");


        UsernamePasswordAuthenticationToken token = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(configProperties.getJwtHeader());
        if (token != null) {
            Claims claims = jwtService.getClaims(token);

            String user = claims.getSubject();

            Date date = claims.getExpiration();
            long now = System.currentTimeMillis();

            if (user != null && date != null && (date.getTime() - now) > 0) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;

    }
}

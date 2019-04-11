package com.share.jwtsecurity.filter;

import com.share.jwtsecurity.config.SecurityConfig;
import com.share.jwtsecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpMethod;
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

    SecurityConfig config;
    private JwtService jwtService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, SecurityConfig config, JwtService jwtService) {
        super(authenticationManager);
        this.config = config;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getMethod().equals(HttpMethod.GET.toString())) {
            chain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(config.getJwtHeader());

        if (null == authorizationHeader || !authorizationHeader.startsWith(config.getJwtTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(config.getJwtHeader());
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

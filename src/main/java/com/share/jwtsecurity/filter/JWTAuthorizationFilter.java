package com.share.jwtsecurity.filter;

import com.share.jwtsecurity.config.SecurityConfig;
import io.jsonwebtoken.Jwts;
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

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    SecurityConfig config;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, SecurityConfig config) {
        super(authenticationManager);
        this.config = config;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getMethod().equals(HttpMethod.GET.toString())) {
            chain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(config.getJwtHeader());

        if (null == header || !header.startsWith(config.getJwtTokenPrefix())) {
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
            String user = Jwts.parser()

                    .setSigningKey(config.getJwtSecretKey())

                    .parseClaimsJws(token.replace(config.getJwtTokenPrefix(), ""))

                    .getBody()

                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;

    }
}

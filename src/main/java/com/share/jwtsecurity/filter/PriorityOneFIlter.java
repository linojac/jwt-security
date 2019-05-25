package com.share.jwtsecurity.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.jwtsecurity.exception.ApiError;
import com.share.jwtsecurity.exception.JwtSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class PriorityOneFIlter extends OncePerRequestFilter {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtSecurityException e) {
            writeErrorResponse(httpServletResponse, e.getApiError());
        } catch (Exception e) {
            writeErrorResponse(httpServletResponse, new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unexpected Error in preprocessing", e));
        }

    }

    private void writeErrorResponse(HttpServletResponse httpServletResponse, ApiError apiError) throws IOException {
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.getWriter().write(convertObjectToJson(apiError));
    }

    private String convertObjectToJson(ApiError apiError) throws JsonProcessingException {

        return objectMapper.writeValueAsString(apiError);
    }
}

package com.share.jwtsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(e.getMessage());
        return buildResponse(apiError);
    }


    private ResponseEntity<Object> buildResponse(ApiError apiError) {
        ResponseEntity<Object> requestEntity = new ResponseEntity<Object>(apiError, apiError.getStatus());
        return requestEntity;
    }
}

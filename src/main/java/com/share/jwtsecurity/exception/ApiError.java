package com.share.jwtsecurity.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {

    private HttpStatus status;

    private String message;

    private String debugMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }


    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }


    public ApiError(HttpStatus status, Throwable throwable) {
        this();
        this.status = status;
        this.message = "Unexpected Error";
        this.debugMessage = throwable.getMessage();
    }


    public ApiError(HttpStatus status, String message, Throwable throwable) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = throwable.getMessage();
    }

}

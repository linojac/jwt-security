package com.share.jwtsecurity.exception;

public class JwtSecurityException extends RuntimeException {

    ApiError apiError;

    public JwtSecurityException(ApiError apiError) {
        super();
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

}

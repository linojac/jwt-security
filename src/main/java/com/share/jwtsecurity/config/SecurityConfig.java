package com.share.jwtsecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret.key:SecretKeyToGenJwtTokens}")
    private String jwtSecretKey;

    @Value("${jwt.expiration.time:864000000}")//10 days
    private long jwtExpirationTime;

    @Value("${jwt.token.prefix:Bearer }")
    private String jwtTokenPrefix;

    @Value("${jwt.header:Authorization}")
    private String jwtHeader;

    @Value(("${sign.up.url:/users/sign-up}"))
    private String signUpUrl;

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public long getJwtExpirationTime() {
        return jwtExpirationTime;
    }

    public void setJwtExpirationTime(long jwtExpirationTime) {
        this.jwtExpirationTime = jwtExpirationTime;
    }

    public String getJwtTokenPrefix() {
        return jwtTokenPrefix;
    }

    public void setJwtTokenPrefix(String jwtTokenPrefix) {
        this.jwtTokenPrefix = jwtTokenPrefix;
    }

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}

package com.share.jwtsecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Value("${jwt.secret.key}")
    private transient String jwtSecretKey;

    @Value("${jwt.expiration.time}")//10 days
    private long jwtExpirationTime;

    @Value("${session.time}")
    private long sessionExpirationTime;

    @Value("${jwt.token.prefix}")
    private String jwtTokenPrefix;

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value(("${sign.up.url}"))
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

    public long getSessionExpirationTime() {
        return sessionExpirationTime;
    }

    public void setSessionExpirationTime(long sessionExpirationTime) {
        this.sessionExpirationTime = sessionExpirationTime;
    }
}

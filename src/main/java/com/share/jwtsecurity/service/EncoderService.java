package com.share.jwtsecurity.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

@Service
public class EncoderService {

    public String encode(String sourceString) throws UnsupportedEncodingException {
        return Base64Utils.encodeToString(sourceString.getBytes("UTF-8"));
    }

    public String decode(String encodedString) {
        byte[] decodedBytes = Base64Utils.decodeFromString(encodedString);
        return new String(decodedBytes);
    }
}

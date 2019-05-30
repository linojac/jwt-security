package com.share.jwtsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.share.commonshare"})
public class JwtSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityApplication.class, args);
    }

}

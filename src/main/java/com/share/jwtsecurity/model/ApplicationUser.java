package com.share.jwtsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationUser {

    @Id
    String id;

    @Indexed(unique = true)
    String username;

    String password;

    String name;

    String email;

    String phoneAreaCode;

    String phoneNumber;


    Set<String> userRoles;

}

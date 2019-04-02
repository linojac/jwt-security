package com.share.jwtsecurity.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class ApplicationUser {

    @Id
    String id;

    String username;

    String password;

    List<String> userRoles;

}

package com.share.jwtsecurity.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Getter
@Setter
public class UserRole {
    @Id
    String id;

    @Indexed(unique = true)
    String name;

    Set<String> rolePrivileges;
}

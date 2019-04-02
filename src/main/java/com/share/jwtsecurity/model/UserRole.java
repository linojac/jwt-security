package com.share.jwtsecurity.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class UserRole {
    @Id
    String id;

    String name;

    List<String> rolePrivileges;
}

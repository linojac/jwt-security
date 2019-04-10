package com.share.jwtsecurity.controller;

import com.share.jwtsecurity.model.ApplicationUser;
import com.share.jwtsecurity.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository,

                          BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.applicationUserRepository = applicationUserRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @PostMapping("/sign-up")

    public void signUp(@RequestBody ApplicationUser user) {

        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRoles(new HashSet<>());
        user.getUserRoles().addAll(Arrays.asList("CUSTOMER"));

        applicationUserRepository.save(user);

    }

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }
}
package com.share.jwtsecurity.controller;

import com.share.jwtsecurity.model.ApplicationUser;
import com.share.jwtsecurity.repository.ApplicationUserRepository;
import com.share.jwtsecurity.service.EncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EncoderService encoderService;

    public UserController(ApplicationUserRepository applicationUserRepository,

                          BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.applicationUserRepository = applicationUserRepository;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @PostMapping("/sign-up")

    public ResponseEntity<String> signUp(@RequestBody ApplicationUser user) {

        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRoles(new HashSet<>());
        user.getUserRoles().addAll(Arrays.asList("CUSTOMER"));

        applicationUserRepository.save(user);

        return new ResponseEntity<>("User Created Successfully!", HttpStatus.OK);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


}
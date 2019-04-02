package com.share.jwtsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.jwtsecurity.model.ApplicationUser;
import com.share.jwtsecurity.model.UserRole;
import com.share.jwtsecurity.repository.ApplicationUserRepository;
import com.share.jwtsecurity.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class JwtSecurityApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtSecurityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityApplication.class, args);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(MongoOperations mongoOperations, ApplicationUserRepository applicationUserRepository,
                                        UserRoleRepository userRoleRepository) {
        if (mongoOperations.collectionExists(ApplicationUser.class)) {
            mongoOperations.dropCollection(ApplicationUser.class);
        }
        ApplicationUser user = new ApplicationUser();
        user.setUsername("lino");
        user.setPassword(bCryptPasswordEncoder().encode("lino"));

        UserRole role = new UserRole();
        role.setName("ADMIN");
        role.setRolePrivileges(new ArrayList<>());
        role.getRolePrivileges().add("READ");
        role.getRolePrivileges().add("WRITE");
        userRoleRepository.save(role);
        userRoleRepository.findAll().forEach(r -> LOGGER.info(r.getName()));
        user.setUserRoles(Arrays.asList("ADMIN"));
        applicationUserRepository.save(user);
        applicationUserRepository.findAll().forEach(u -> LOGGER.info(u.getUsername()));
        return null;
    }
}

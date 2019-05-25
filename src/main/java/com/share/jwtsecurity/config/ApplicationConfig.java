package com.share.jwtsecurity.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.jwtsecurity.repository.ApplicationUserRepository;
import com.share.jwtsecurity.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        return objectMapper;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(MongoOperations mongoOperations, ApplicationUserRepository applicationUserRepository,
                                        UserRoleRepository userRoleRepository) {
//        if (mongoOperations.collectionExists(ApplicationUser.class)) {
//            mongoOperations.dropCollection(ApplicationUser.class);
//        }
//        if (mongoOperations.collectionExists(UserRole.class)) {
//            mongoOperations.dropCollection(UserRole.class);
//        }
//        ApplicationUser user = new ApplicationUser();
//        user.setUsername("admin");
//        user.setPassword(bCryptPasswordEncoder().encode("admin"));
//
//        UserRole role = new UserRole();
//        role.setName("ADMIN");
//        role.setRolePrivileges(new HashSet<>());
//        role.getRolePrivileges().add("READ");
//        role.getRolePrivileges().add("WRITE");
//        userRoleRepository.save(role);
//        userRoleRepository.findAll().forEach(r -> LOGGER.info(r.getName()));
//        user.setUserRoles(new HashSet<>());
//        user.getUserRoles().addAll(Arrays.asList("ADMIN"));
//        applicationUserRepository.save(user);
        applicationUserRepository.findAll().forEach(u -> LOGGER.info(u.getUsername()));
        return null;
    }
}

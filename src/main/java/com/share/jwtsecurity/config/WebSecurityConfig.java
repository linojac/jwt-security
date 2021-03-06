package com.share.jwtsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.jwtsecurity.filter.JWTAuthenticationFilter;
import com.share.jwtsecurity.filter.JWTAuthorizationFilter;
import com.share.jwtsecurity.service.EncoderService;
import com.share.jwtsecurity.service.JwtService;
import com.share.jwtsecurity.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    ConfigProperties configProperties;

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ObjectMapper objectMapper;
    private JwtService jwtService;
    private EncoderService encoderService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                             ObjectMapper objectMapper, ConfigProperties configProperties, JwtService jwtService,
                             EncoderService encoderService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
        this.configProperties = configProperties;
        this.jwtService = jwtService;
        this.encoderService = encoderService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()

                .antMatchers(HttpMethod.POST, configProperties.getSignUpUrl()).permitAll()
//                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(),
                        objectMapper, configProperties, jwtService, encoderService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), configProperties, jwtService));


    }

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addExposedHeader("Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}

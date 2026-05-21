package com.skillbridge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Tells Spring: "This file contains tools for the toolbox"
@EnableWebSecurity // Enables the security bouncer
public class SecurityConfig {

    // This is our "Hammer" - the tool that hashes passwords
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This is the "Bouncer's Instructions" - who gets in and who doesn't
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Add Swagger/OpenAPI paths here
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 2. Your existing API paths
                        .requestMatchers("/api/users/**", "/api/workshops/**").permitAll()

                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
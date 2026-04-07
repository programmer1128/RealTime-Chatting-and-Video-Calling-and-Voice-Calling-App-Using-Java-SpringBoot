package com.backend.backend_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for development (since React Native uses fetch)
            .csrf(csrf -> csrf.disable())

            // Allow all endpoints
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/users/**",        // all user-related endpoints
                    "/messages/**",     // all message-related endpoints
                    "/ws/**",
                    "/topic/**",
                    "/groups/**",
                    "/messages/group/**",
                    "/ws-chat/**"     
                ).permitAll()
                .anyRequest().permitAll()
            )

            // Disable form login and HTTP Basic (since it’s a REST API)
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable())

            // Disable session creation (optional for stateless APIs)
            .sessionManagement(session -> session.disable());

        return http.build();
    }
}
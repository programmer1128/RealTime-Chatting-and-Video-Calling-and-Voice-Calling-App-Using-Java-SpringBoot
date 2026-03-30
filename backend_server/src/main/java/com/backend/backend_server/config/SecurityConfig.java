package com.backend.backend_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig 
{

     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/video-call/**").permitAll() // <-- ADD THIS LINE
                .requestMatchers("/admin/create-user/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/ws-chat/**").permitAll() 
                .anyRequest().authenticated()
            .and()
            .httpBasic();

        return http.build();
    }

     @Bean
     public PasswordEncoder passwordEncoder()
     {
         return new BCryptPasswordEncoder();
     }

     @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("*")); // or list of explicit origins
    configuration.setAllowCredentials(true);
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

}

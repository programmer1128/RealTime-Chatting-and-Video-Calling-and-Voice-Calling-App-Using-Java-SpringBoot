// package com.backend.backend_server;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         // Disable CSRF for simple API testing (optional, but common)
        

//         http
//             .csrf(CsrfConfigurer::disable)
//             .authorizeHttpRequests((requests) -> requests
//                 // Allow the /hello endpoint without any authentication
//                 .requestMatchers("/", "/**").permitAll()
//                 // Require authentication for all other requests (if any)
//                 .anyRequest().authenticated()
//             )
//             // We don't need basic authentication if /hello is permitted
//             .httpBasic(Customizer.withDefaults()); 

//         return http.build();
//     }
// }
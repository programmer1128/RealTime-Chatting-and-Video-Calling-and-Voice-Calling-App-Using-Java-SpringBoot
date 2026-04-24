package com.backend.backend_server.util;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    // Utility for token encryption, claims, and signing key management.

    public String generateToken(String subject) {
        // TODO: Implementation logic for creating a signed JWT.
        return "mock.jwt.token";
    }

    public boolean validateToken(String token) {
        // TODO: Implementation logic for validating token signature and expiry.
        return true;
    }

    public String getUsernameFromToken(String token) {
        // TODO: Implementation logic for extracting the username (subject) from token claims.
        return "testUser";
    }
}
/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil 
{
     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

     // Hashing utility for passwords
     public String hashPassword(String rawPassword) 
     {
         return passwordEncoder.encode(rawPassword);
     }

     // Verification utility for login
     public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    // Placeholder for C++ bridge logic if needed for secure key operations
     public String bridgeToCppSecureOperation(String data) 
     {
         // Implementation logic here
         return "secure_data";
     }
}
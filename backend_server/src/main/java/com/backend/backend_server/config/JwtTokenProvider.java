/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.config;

import com.backend.backend_server.entity.User;
import com.backend.backend_server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider 
{

     @Autowired
     private JwtUtil jwtUtil;

     // Creates and validates tokens using JwtUtil (Abstraction layer)

     public String generateAccessToken(User user) 
     {
         // Add logic to use user details (ID, Role, Username) as claims.
         return jwtUtil.generateToken(user.getUsername());
     }

     public String generateRefreshToken(User user) 
     {
         // Generate a separate, longer-lived refresh token.
         return "mock.refresh.token";
     }

     public boolean validateToken(String token) 
     {
         return jwtUtil.validateToken(token);
     }

     public String getUsernameFromToken(String token) 
     {
         return jwtUtil.getUsernameFromToken(token);
     }
}
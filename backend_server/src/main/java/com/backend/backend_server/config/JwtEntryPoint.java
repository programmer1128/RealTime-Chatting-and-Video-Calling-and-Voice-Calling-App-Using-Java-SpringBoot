package com.backend.backend_server.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint 
{
     // Handles token authentication errors (e.g., 401 Unauthorized)
     @Override
     public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException 
     {

         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + authException.getMessage());
     }
}
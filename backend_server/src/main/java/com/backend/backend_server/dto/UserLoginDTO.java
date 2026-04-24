package com.backend.backend_server.dto;

import com.backend.backend_server.entity.Role;

public record UserLoginDTO(
    String username,
    int otp
) {}

package com.backend.backend_server.dto;
import com.backend.backend_server.entity.Role;

public record AuthResponseDTO (
    String accessToken,
    String refreshToken,
    Long userId,
    Role role,
    String status
) {};

package com.backend.backend_server.dto;

import com.backend.backend_server.entity.Role;

public record BeforeLoginDTO(
    Long id,
    String username,
    Role role,
    String status
) {}

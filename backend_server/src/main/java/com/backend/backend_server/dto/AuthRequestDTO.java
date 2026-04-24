package com.backend.backend_server.dto;

public record AuthRequestDTO(
    String username,
    String email,
    String password
) {}
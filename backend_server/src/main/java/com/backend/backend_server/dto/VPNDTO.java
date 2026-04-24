package com.backend.backend_server.dto;

import java.time.LocalDateTime;

public record VPNDTO (
    String sessionId,
    Long userId,
    String username,
    String clientIp,
    String gatewayIp,
    LocalDateTime lastActive,
    String status
) {}

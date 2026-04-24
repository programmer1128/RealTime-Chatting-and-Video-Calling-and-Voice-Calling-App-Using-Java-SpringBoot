package com.backend.backend_server.dto;

import java.time.LocalDateTime;

public record AuditLogDTO (
    LocalDateTime timestamp,
    String action,
    String performedByRole,
    Long userId,
    String details
) {}
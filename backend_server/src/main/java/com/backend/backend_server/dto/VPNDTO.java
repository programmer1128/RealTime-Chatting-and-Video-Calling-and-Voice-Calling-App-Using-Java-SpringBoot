/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

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

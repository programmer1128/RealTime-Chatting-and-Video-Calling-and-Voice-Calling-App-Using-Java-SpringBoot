/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.dto;

import com.backend.backend_server.entity.Role;

public record BeforeLoginDTO(
    Long id,
    String username,
    Role role,
    String status
) {}

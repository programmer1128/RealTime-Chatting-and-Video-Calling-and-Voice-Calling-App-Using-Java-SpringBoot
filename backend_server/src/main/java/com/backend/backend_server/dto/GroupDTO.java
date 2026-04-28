/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.dto;

import java.util.Set;

public record GroupDTO (
    Long id,
    String groupName,
    Set<Long> memberIds // IDs of users in the group
) {}
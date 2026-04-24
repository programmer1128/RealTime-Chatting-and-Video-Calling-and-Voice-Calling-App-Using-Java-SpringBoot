package com.backend.backend_server.dto;

import java.util.Set;

public record GroupDTO (
    Long id,
    String groupName,
    Set<Long> memberIds // IDs of users in the group
) {}
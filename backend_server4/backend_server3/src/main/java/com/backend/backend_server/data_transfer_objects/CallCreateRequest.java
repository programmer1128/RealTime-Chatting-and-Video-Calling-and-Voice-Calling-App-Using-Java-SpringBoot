package com.backend.backend_server.data_transfer_objects;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for creating a new call session.
 * This class models the JSON payload that the client sends to the /api/calls/create endpoint.
 */
public class CallCreateRequest {

    private UUID initiatorId;
    private UUID groupId; // Optional: ID of a group if the call is for a specific group
    private Integer maxParticipants;

    // Getters and Setters are required for Jackson (the JSON library) to work.

    public UUID getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(UUID initiatorId) {
        this.initiatorId = initiatorId;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}


package com.backend.backend_server.data_transfer_objects;

import java.util.UUID;

public class CallJoinRequest {
    private UUID callSessionId;
    private UUID userId;
    private String clientEndpoint; // WebRTC or IP:Port identifier

    // --- Getters and Setters ---
    public UUID getCallSessionId() { return callSessionId; }
    public void setCallSessionId(UUID callSessionId) { this.callSessionId = callSessionId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getClientEndpoint() { return clientEndpoint; }
    public void setClientEndpoint(String clientEndpoint) { this.clientEndpoint = clientEndpoint; }
}

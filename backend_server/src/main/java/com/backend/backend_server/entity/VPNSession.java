package com.backend.backend_server.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vpn_sessions")
public class VPNSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;
    private String clientIp;
    private String gatewayIp;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime lastActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VPNSession() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }

    public String getGatewayIp() { return gatewayIp; }
    public void setGatewayIp(String gatewayIp) { this.gatewayIp = gatewayIp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getLastActive() { return lastActive; }
    public void setLastActive(LocalDateTime lastActive) { this.lastActive = lastActive; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

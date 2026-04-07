package com.backend.backend_server.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_participants")
public class CallParticipant {

    public enum Role {
        HOST,       // The user who initiated the call
        PARTICIPANT, // A regular participant
        OBSERVER    // Someone who can only watch (future feature)
    }

    @Id
    private UUID id;

    // This creates a many-to-one relationship with CallSession.
    // Many participants can belong to one session.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_session_id", nullable = false)
    private CallSession callSession;

    // NOTE: This assumes your teammate has a 'User' entity.
    // You may need to change 'private UUID userId' to 'private User user'
    // and add the appropriate @ManyToOne mapping.
    private UUID userId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private Instant joinTime;
    private Instant leaveTime;

    private String clientEndpoint; // e.g., IP address or device ID

    // Constructors
    public CallParticipant() {
        this.id = UUID.randomUUID();
        this.joinTime = Instant.now();
    }

    public CallParticipant(CallSession callSession, UUID userId, Role role, String clientEndpoint) {
        this(); // Calls the default constructor
        this.callSession = callSession;
        this.userId = userId;
        this.role = role;
        this.clientEndpoint = clientEndpoint;
    }


    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CallSession getCallSession() {
        return callSession;
    }

    public void setCallSession(CallSession callSession) {
        this.callSession = callSession;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Instant joinTime) {
        this.joinTime = joinTime;
    }

    public Instant getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Instant leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getClientEndpoint() {
        return clientEndpoint;
    }

    public void setClientEndpoint(String clientEndpoint) {
        this.clientEndpoint = clientEndpoint;
    }
}


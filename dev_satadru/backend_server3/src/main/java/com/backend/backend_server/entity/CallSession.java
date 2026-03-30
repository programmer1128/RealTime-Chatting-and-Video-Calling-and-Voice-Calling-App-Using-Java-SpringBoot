package com.backend.backend_server.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_sessions")
public class CallSession {

    public enum Status {
        INITIATED, // Call has been created but no one has joined yet
        RINGING,   // At least one participant has joined, call is active
        ENDED,     // Call has been terminated
        REJECTED
    }

    @Id
    private UUID id;

    private UUID groupId;
    private UUID initiatorId;
    private String encryptionKeyId;
    private String sfuRoomId; // The ID used for the WebSocket room
    private Integer maxParticipants;
    private boolean recorded;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Instant createdAt;
    private Instant startedAt;
    private Instant endedAt;

    // Constructors
    public CallSession() {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.status = Status.INITIATED;
        this.recorded = false; // Default to not recorded
    }

    public CallSession(UUID groupId, UUID initiatorId, String encryptionKeyId, String sfuRoomId) {
        this(); // Calls the default constructor to set UUID, createdAt, etc.
        this.groupId = groupId;
        this.initiatorId = initiatorId;
        this.encryptionKeyId = encryptionKeyId;
        this.sfuRoomId = sfuRoomId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public UUID getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(UUID initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getEncryptionKeyId() {
        return encryptionKeyId;
    }

    public void setEncryptionKeyId(String encryptionKeyId) {
        this.encryptionKeyId = encryptionKeyId;
    }

    public String getSfuRoomId() {
        return sfuRoomId;
    }

    public void setSfuRoomId(String sfuRoomId) {
        this.sfuRoomId = sfuRoomId;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void setRecorded(boolean recorded) {
        this.recorded = recorded;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Instant endedAt) {
        this.endedAt = endedAt;
    }
}


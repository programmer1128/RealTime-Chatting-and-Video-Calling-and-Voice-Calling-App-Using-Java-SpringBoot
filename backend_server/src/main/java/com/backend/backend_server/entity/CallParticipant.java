package com.backend.backend_server.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a single user's participation in a specific CallSession.
 * This acts as a "join table" between Users and CallSessions.
 */
@Entity
@Table(name = "call_participants")
public class CallParticipant {

    /**
     * Defines the role of a user within a call.
     */
   

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "call_session_id", nullable = false)
    private CallSession callSession;

    // --- THIS IS THE MAJOR CHANGE IN THIS FILE ---
    @ManyToOne // Establishes a many-to-one relationship with the User entity
    @JoinColumn(name = "user_id", nullable = false) // Defines the foreign key column
    private User user;
    // --- END OF CHANGE ---

   
    @Column(nullable = false)
    private String role;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt = Instant.now();

    // --- Constructors ---

    // JPA requires a no-argument constructor
    public CallParticipant() {}

    public CallParticipant(CallSession callSession, User user, String role) {
        this.callSession = callSession;
        this.user = user;
        this.role = role;
    }

    // --- Getters and Setters (Updated for the 'user' field) ---

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }
}


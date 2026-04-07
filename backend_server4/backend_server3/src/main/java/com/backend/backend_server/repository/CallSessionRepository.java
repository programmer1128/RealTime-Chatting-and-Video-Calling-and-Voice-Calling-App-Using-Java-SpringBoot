package com.backend.backend_server.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_server.entity.CallSession;

/**
 * Spring Data JPA repository for the CallSession entity.
 * This interface provides CRUD (Create, Read, Update, Delete) operations
 * for the 'call_sessions' table in the database.
 */
@Repository
public interface CallSessionRepository extends JpaRepository<CallSession, UUID> {

    /**
     * Finds a call session by its unique SFU (Selective Forwarding Unit) room ID.
     * This is useful for looking up calls using the ID that the WebSocket handler uses.
     *
     * @param sfuRoomId The SFU room ID to search for.
     * @return An Optional containing the CallSession if found, or an empty Optional otherwise.
     */
    Optional<CallSession> findBySfuRoomId(String sfuRoomId);

    // Spring Data JPA automatically provides methods like:
    // - save(CallSession session)
    // - findById(UUID id)
    // - findAll()
    // - deleteById(UUID id)
    // ... and many more.
}


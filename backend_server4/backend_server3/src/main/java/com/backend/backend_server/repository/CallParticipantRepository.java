package com.backend.backend_server.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_server.entity.CallParticipant;
import com.backend.backend_server.entity.CallSession;

/**
 * Spring Data JPA repository for the CallParticipant entity.
 * This provides CRUD operations for the 'call_participants' table.
 */
@Repository
public interface CallParticipantRepository extends JpaRepository<CallParticipant, UUID> {

    /**
     * Finds all participants associated with a specific call session.
     * This is useful for getting a list of everyone in a call.
     *
     * @param callSession The CallSession entity to find participants for.
     * @return A List of CallParticipants in that session.
     */
    List<CallParticipant> findByCallSession(CallSession callSession);

    /**
     * Checks if a specific user is already a participant in a specific call session.
     * This helps prevent duplicate entries when a user tries to join a call multiple times.
     *
     * @param callSession The CallSession to check.
     * @param userId The ID of the user to check for.
     * @return true if the user is already a participant, false otherwise.
     */
    boolean existsByCallSessionAndUserId(CallSession callSession, UUID userId);

}


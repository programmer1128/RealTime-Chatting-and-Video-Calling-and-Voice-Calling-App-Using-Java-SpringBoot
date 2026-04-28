/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_server.entity.CallParticipant;
import com.backend.backend_server.entity.CallSession;
import com.backend.backend_server.entity.User;

/**
 * Spring Data JPA repository for the CallParticipant entity.
 */
@Repository
public interface CallParticipantRepository extends JpaRepository<CallParticipant, UUID> {

    /**
     * Finds all participants associated with a specific call session.
     * Spring Data JPA automatically implements this based on the method name.
     * @param callSession The call session to find participants for.
     * @return A list of all participants in the call.
     */
    List<CallParticipant> findByCallSession(CallSession callSession);

    /**
     * Finds all call participations for a specific user.
     * UPDATED: This now takes a User object instead of a UUID.
     * @param user The user to find participations for.
     * @return A list of all call participations for the user.
     */
    List<CallParticipant> findByUser(User user);

    /**
     * Counts the number of participants in a specific call session.
     * @param callSession The call session to count participants in.
     * @return The number of participants.
     */
    long countByCallSession(CallSession callSession);
    
    /**
     * NEW METHOD: Checks if a user is a participant in any call session
     * with a given status. This is crucial for our "one active call per user" rule.
     * @param user The user to check.
     * @param status The status of the call to check for (e.g., "ACTIVE").
     * @return true if the user is in an active call, false otherwise.
     */
    boolean existsByUserAndCallSession_CallStatus(User user, String callStatus);
}


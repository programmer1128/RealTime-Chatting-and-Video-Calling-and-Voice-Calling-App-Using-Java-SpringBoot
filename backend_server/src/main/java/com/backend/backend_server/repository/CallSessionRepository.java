/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend_server.entity.CallSession;

public interface CallSessionRepository extends JpaRepository<CallSession, UUID> 
{
    //List<CallSession> findByGroupIdAndStatus(String groupId, CallSession.Status status);
     Optional<CallSession> findBySfuRoomId(String sfuRoomId);
    //List<CallSession> findByStatus(CallSession.Status status);
}

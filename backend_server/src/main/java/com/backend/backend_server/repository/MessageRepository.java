/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.backend.backend_server.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds all messages for a specific group chat, sorted by the time they were sent.
     *
     * @param groupId The ID of the group to fetch messages for.
     * @return A list of messages, sorted from oldest to newest.
     */
    List<Message> findByGroupIdOrderByTimestampAsc(String groupId);

    /**
     * Finds direct messages between two specific users.
     *
     * @param user1 The ID of the first user.
     * @param user2 The ID of the second user.
     * @return A list of messages between the two users, sorted chronologically.
     */
    @Query("SELECT m FROM Message m " +
           "WHERE (m.sender.id = :user1 AND m.receiverId = :user2) " +
           "   OR (m.sender.id = :user2 AND m.receiverId = :user1) " +
           "ORDER BY m.timestamp ASC")
    List<Message> findChatBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);
}

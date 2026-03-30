package com.backend.backend_server.repository;

import com.backend.backend_server.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;      
import org.springframework.data.repository.query.Param; 
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m " +
           "WHERE (m.senderId = :user1 AND m.receiverId = :user2) " +
           "   OR (m.senderId = :user2 AND m.receiverId = :user1) " +
           "ORDER BY m.timestamp ASC")
    List<Message> findChatBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);
}
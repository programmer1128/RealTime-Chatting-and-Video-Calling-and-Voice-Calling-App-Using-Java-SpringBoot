package com.backend.backend_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_server.entity.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> 
{

    /**
     * Finds all messages for a specific group chat, sorted by the time they were sent.
     * Spring Data JPA automatically creates the implementation for this method
     * based on its name.
     *
     * @param groupId The ID of the group to fetch messages for.
     * @return A list of messages, sorted from oldest to newest.
     */
     List<Message> findByGroupIdOrderByTimestampAsc(String groupId);
}

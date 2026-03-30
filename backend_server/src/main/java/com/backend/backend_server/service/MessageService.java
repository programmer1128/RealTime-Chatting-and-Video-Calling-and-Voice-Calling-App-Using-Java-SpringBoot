package com.backend.backend_server.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_server.entity.Message;
import com.backend.backend_server.repository.MessageRepository;

/**
 * Service class for handling all business logic related to chat messages.
 */
@Service
public class MessageService  
{

     @Autowired
     private MessageRepository messageRepository;

     /**
     * Retrieves all messages for a specific group, sorted by timestamp.
     * @param groupId The ID of the group.
     * @return A list of messages.
     */
     public List<Message> findMessagesByGroupId(String groupId) 
     {
         // We delegate the call directly to our repository's custom method.
         return messageRepository.findByGroupIdOrderByTimestampAsc(groupId);
     }

     /**
     * Saves a new message to the database. It sets the timestamp to the current moment
     * before saving.
     * @param message The message entity to save.
     * @return The saved message entity (which will now have a database-generated ID).
     */
     public Message saveMessage(Message message) 
     {
         // Set the timestamp right before saving to ensure it's accurate.
         message.setTimestamp(Instant.now());
         return messageRepository.save(message);
     }
}

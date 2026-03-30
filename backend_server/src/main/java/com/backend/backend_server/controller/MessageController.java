package com.backend.backend_server.controller;

import com.backend.backend_server.entity.Message;
import com.backend.backend_server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller to handle both REST API requests for message history
 * and WebSocket messages for live chat.
 */
@Controller //
@RequestMapping("/api")
public class MessageController 
{

     // inject a MessageService to handle the business logic.
     @Autowired
     private MessageService messageService;

     /**
     * REST Endpoint (HTTP GET)
     * Fetches the historical messages for a specific group chat.
     * The app will call this endpoint when the user opens a chat screen.
     *
     * @param groupId The ID of the group to fetch messages for.
     * @return A list of all messages for that group.
     */
     @GetMapping("/messages/group/{groupId}")
     public ResponseEntity<List<Message>> getGroupMessages(@PathVariable String groupId) 
     {
         List<Message> messages = messageService.findMessagesByGroupId(groupId);
         return ResponseEntity.ok(messages);
     }

     /**
     * WebSocket Endpoint (STOMP)
     * This method is invoked when a message is sent to a destination like "/app/group-chat/{groupId}".
     * It saves the message to the database and then broadcasts it to all subscribers
     * of the "/topic/group/{groupId}" destination.
     *
     * @param message The chat message sent by the client.
     * @param groupId The group ID from the destination path.
     * @return The saved message, which will be broadcast by the @SendTo annotation.
     */
     @MessageMapping("/group-chat/{groupId}")
     @SendTo("/topic/group/{groupId}")
     public Message handleGroupMessage(@Payload Message message, @DestinationVariable String groupId) 
     {
         // The message is saved to the database.
         Message savedMessage = messageService.saveMessage(message);
         // The saved message (now with a real ID and timestamp) is returned and broadcast.
         return savedMessage;
     }
}

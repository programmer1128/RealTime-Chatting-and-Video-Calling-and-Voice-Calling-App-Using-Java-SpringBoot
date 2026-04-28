/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.controller;

import com.backend.backend_server.entity.Message;
import com.backend.backend_server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId,
                                             @RequestParam String content) {
        return ResponseEntity.ok(messageService.sendMessage(senderId, receiverId, content));
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Message>> getChat(@RequestParam Long user1, @RequestParam Long user2) {
        return ResponseEntity.ok(messageService.getChat(user1, user2));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Message>> getGroupMessages(@PathVariable String groupId) {
        return ResponseEntity.ok(messageService.findMessagesByGroupId(groupId));
    }

    @MessageMapping("/group-chat/{groupId}")
    @SendTo("/topic/group/{groupId}")
    public Message handleGroupMessage(@Payload Message message, @DestinationVariable String groupId) {
        message.setGroupId(groupId);
        return messageService.saveMessage(message);
    }

    @MessageMapping("/chat.send")
    public void sendMessageWebSocket(Message msg) {
        Message saved = messageService.sendMessage(msg.getSender().getId(), msg.getReceiverId(), msg.getContent());
        messagingTemplate.convertAndSend("/topic/messages." + msg.getReceiverId(), saved);
        messagingTemplate.convertAndSend("/topic/messages." + saved.getSender().getId(), saved);
    }
}

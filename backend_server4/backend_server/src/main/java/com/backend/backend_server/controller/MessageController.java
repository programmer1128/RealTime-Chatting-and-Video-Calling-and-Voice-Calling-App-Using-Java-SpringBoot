package com.backend.backend_server.controller;

import com.backend.backend_server.entity.Message;
import com.backend.backend_server.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/send")
    public Message sendMessage(@RequestParam Long senderId, @RequestParam Long receiverId,
            @RequestParam String content) {
        return messageService.sendMessage(senderId, receiverId, content);
    }

    @GetMapping("/chat")
    public List<Message> getChat(@RequestParam Long user1, @RequestParam Long user2) {
        return messageService.getChat(user1, user2);
    }

    // For STOMP WebSocket
    @MessageMapping("/chat.send")
    public void sendMessageWebSocket(Message msg) {
        Message saved = messageService.sendMessage(msg.getSenderId(), msg.getReceiverId(), msg.getContent());
        // send to the receiver's topic
        messagingTemplate.convertAndSend("/topic/messages." + msg.getReceiverId(), saved);
        // send to sender for updating their own chat
        messagingTemplate.convertAndSend("/topic/messages." + msg.getSenderId(), saved);
    }

}

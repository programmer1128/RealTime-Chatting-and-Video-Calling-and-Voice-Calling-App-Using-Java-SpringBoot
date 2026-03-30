package com.backend.backend_server.service;

import com.backend.backend_server.entity.Message;
import com.backend.backend_server.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    public MessageService(MessageRepository messageRepository) { this.messageRepository = messageRepository; }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        Message msg = new Message(senderId, receiverId, content, LocalDateTime.now());
        return messageRepository.save(msg);
    }

    public List<Message> getChat(Long userId1, Long userId2) {
        return messageRepository.findChatBetweenUsers(userId1, userId2);
    }

}

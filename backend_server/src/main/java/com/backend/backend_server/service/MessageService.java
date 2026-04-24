package com.backend.backend_server.service;

import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.backend_server.entity.Message;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.MessageRepository;
import com.backend.backend_server.repository.UserRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Message> findMessagesByGroupId(String groupId) {
        return messageRepository.findByGroupIdOrderByTimestampAsc(groupId);
    }

    public Message saveMessage(Message message) {
        message.setTimestamp(Instant.now());
        return messageRepository.save(message);
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Message msg = new Message(sender, receiverId, null, content, Instant.now());
        return messageRepository.save(msg);
    }

    public List<Message> getChat(Long userId1, Long userId2) {
        return messageRepository.findChatBetweenUsers(userId1, userId2);
    }
}

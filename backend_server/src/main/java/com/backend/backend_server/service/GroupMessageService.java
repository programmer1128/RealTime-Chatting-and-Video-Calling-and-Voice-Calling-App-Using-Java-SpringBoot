/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.service;

import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.GroupMessage;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.GroupMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupMessageService {

    private final GroupMessageRepository groupMessageRepository;

    public GroupMessageService(GroupMessageRepository groupMessageRepository) {
        this.groupMessageRepository = groupMessageRepository;
    }

    public GroupMessage sendMessage(Group group, User sender, String content) {
        GroupMessage msg = new GroupMessage(group, sender, content, LocalDateTime.now());
        return groupMessageRepository.save(msg);
    }

    public List<GroupMessage> getMessages(Group group) {
        return groupMessageRepository.findByGroupOrderByTimestampAsc(group);
    }

    public List<GroupMessage> getMessagesForGroup(Group group) {
        return groupMessageRepository.findByGroupOrderByTimestampAsc(group);
    }

}

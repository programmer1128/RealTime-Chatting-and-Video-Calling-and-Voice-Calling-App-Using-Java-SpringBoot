/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.service;

import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.GroupRepository;
import com.backend.backend_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminGroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(String name, List<Long> userIds) {
        Set<User> members = new HashSet<>(userRepository.findAllById(userIds));
        Group group = new Group();
        group.setName(name);
        group.setCreatedAt(LocalDateTime.now());
        group.setMembers(members);
        return groupRepository.save(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    public Group updateGroupMembers(Long groupId, List<Long> userIds) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        Set<User> members = new HashSet<>(userRepository.findAllById(userIds));
        group.setMembers(members);
        return groupRepository.save(group);
    }
}

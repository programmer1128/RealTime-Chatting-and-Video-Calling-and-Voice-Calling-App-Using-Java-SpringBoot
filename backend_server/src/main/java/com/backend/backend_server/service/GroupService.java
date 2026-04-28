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
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<Group> getAllGroups() { return groupRepository.findAll(); }
    public Group getGroupById(Long id) { return groupRepository.findById(id).orElse(null); }

    public Group addMembers(Long groupId, List<Long> userIds) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
            Set<User> members = new HashSet<>(userRepository.findAllById(userIds));
            group.getMembers().addAll(members);
            return groupRepository.save(group);
        }
        return null;
    }

    public List<Group> getUserGroups(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return groupRepository.findAll().stream()
                .filter(g -> g.getMembers().contains(user))
                .collect(Collectors.toList());
        }
        return List.of();
    }
}

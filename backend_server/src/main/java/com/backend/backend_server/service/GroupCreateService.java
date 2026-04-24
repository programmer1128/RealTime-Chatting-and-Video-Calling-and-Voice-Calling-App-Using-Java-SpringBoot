package com.backend.backend_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.backend.backend_server.data_transfer_objects.GroupCreateRequest;
import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.GroupRepository;
import com.backend.backend_server.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class GroupCreateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private GroupRepository groupRepository;

    @Transactional
    public Group createGroup(GroupCreateRequest request) {
        Group newGroup = new Group();
        newGroup.setName(request.getGroupName());
        newGroup.setCreatedAt(LocalDateTime.now());
        newGroup.setMembers(new HashSet<>());
        
        for (String username : request.getUserNames()) {
            User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
            
            newGroup.getMembers().add(user);
            user.getGroups().add(newGroup);
        }
        
        return groupRepository.save(newGroup);
    }
}

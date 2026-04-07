package com.backend.backend_server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.GroupRepository;
import com.backend.backend_server.repository.UserRepository;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupDTO dto) {
        if(groupRepository.findByGroupName(dto.getGroupName()).isPresent()) {
            return ResponseEntity.badRequest().body("Group name already exists");
        }

        Set<User> membersSet = new HashSet<>();
        for (Long userId : dto.getMemberIds()) {
            Optional<User> userOpt = userRepository.findById(userId);
            if(userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID "+userId+" not found");
            }
            membersSet.add(userOpt.get());
        }

        Group group = new Group();
        group.setGroupName(dto.getGroupName());
        group.setMembers(membersSet);

        Group savedGroup = groupRepository.save(group);
        return ResponseEntity.ok(savedGroup);
    }

    @GetMapping("/")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // DTO class for input
    public static class GroupDTO {
        private String groupName;
        private Set<Long> memberIds;

        public String getGroupName() { return groupName; }
        public void setGroupName(String groupName) { this.groupName = groupName; }

        public Set<Long> getMemberIds() { return memberIds; }
        public void setMemberIds(Set<Long> memberIds) { this.memberIds = memberIds; }
    }
}

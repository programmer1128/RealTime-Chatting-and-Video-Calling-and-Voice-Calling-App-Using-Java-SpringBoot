package com.backend.backend_server.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.GroupMessage;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;
import com.backend.backend_server.service.GroupMessageService;
import com.backend.backend_server.service.GroupService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/groups")
public class GroupChatController {

    private final GroupService groupService;
    private final GroupMessageService groupMessageService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public GroupChatController(GroupService groupService,
            GroupMessageService groupMessageService,
            UserRepository userRepository,
            SimpMessagingTemplate messagingTemplate) {
        this.groupService = groupService;
        this.groupMessageService = groupMessageService;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // --------------------- REST ENDPOINTS ---------------------

    @PostMapping("/create")
    public Group createGroup(@RequestBody GroupCreateDTO dto) {
        User creator = userRepository.findById(dto.getCreatorId()).orElseThrow();
        List<User> members = dto.getMemberIds().stream()
                .map(id -> userRepository.findById(id).orElseThrow())
                .collect(Collectors.toList());
        if (!members.contains(creator))
            members.add(creator);
        return groupService.createGroup(dto.getName(), creator, members);
    }

    @GetMapping("/my-groups/{userId}")
    public List<Group> getGroupsForUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return groupService.getGroupsForUser(user);
    }

    @GetMapping("/messages")
    public List<GroupMessage> getMessages(@RequestParam Long groupId) {
        Group group = groupService.getGroupById(groupId); // direct lookup by groupId
        return groupMessageService.getMessagesForGroup(group);
    }

    // --------------------- WEBSOCKET MESSAGING ---------------------

    @MessageMapping("/group.send")
    public void sendGroupMessage(GroupMessageDTO dto) {
        User sender = userRepository.findById(dto.getSenderId()).orElseThrow();
        Group group = groupService.getGroupById(dto.getGroupId());
        GroupMessage savedMsg = groupMessageService.sendMessage(group, sender, dto.getContent());

        // Send message to all subscribed clients for this group
        messagingTemplate.convertAndSend("/topic/group." + group.getId(), savedMsg);
    }

    // --------------------- DTO CLASS ---------------------

    public static class GroupMessageDTO {
        private Long groupId;
        private Long senderId;
        private String content;

        public GroupMessageDTO() {
        }

        public GroupMessageDTO(Long groupId, Long senderId, String content) {
            this.groupId = groupId;
            this.senderId = senderId;
            this.content = content;
        }

        public Long getGroupId() {
            return groupId;
        }

        public void setGroupId(Long groupId) {
            this.groupId = groupId;
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class GroupCreateDTO {
        private String name;
        private Long creatorId;
        private List<Long> memberIds;

        public GroupCreateDTO() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Long creatorId) {
            this.creatorId = creatorId;
        }

        public List<Long> getMemberIds() {
            return memberIds;
        }

        public void setMemberIds(List<Long> memberIds) {
            this.memberIds = memberIds;
        }
    }
}

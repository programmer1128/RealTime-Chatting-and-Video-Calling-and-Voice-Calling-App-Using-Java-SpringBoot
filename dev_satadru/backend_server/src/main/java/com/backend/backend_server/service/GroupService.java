package com.backend.backend_server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.GroupRepository;

@Service
public class GroupService 
{
     @Autowired
     private final GroupRepository groupRepository;

     public GroupService(GroupRepository groupRepository) 
     {
         this.groupRepository = groupRepository;
     }

     public Group createGroup(String name, User creator, List<User> members) 
     {
        Group group = new Group(name, creator, members, LocalDateTime.now());
        return groupRepository.save(group);
    }

     public List<Group> getGroupsForUser(User user) 
     {
         return groupRepository.findByMembersContaining(user);
     }

     public Group getGroupById(Long groupId) 
     {
         return groupRepository.findById(groupId).orElse(null);
     }

}

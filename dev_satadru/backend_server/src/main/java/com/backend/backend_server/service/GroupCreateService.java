package com.backend.backend_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Import new Group entity
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend_server.data_transfer_objects.GroupCreateRequest; // Import new GroupRepository
import com.backend.backend_server.entity.GroupEntity;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.GroupEntityRepository;
import com.backend.backend_server.repository.UserRepository;

@Service
public class GroupCreateService 
{

      @Autowired
      private UserRepository userRepository;

      @Autowired 
      private GroupEntityRepository groupRepository;

      @Transactional
      public GroupEntity createGroup(GroupCreateRequest request) 
      {
           //Create the GroupEntity object
           GroupEntity newGroup = new GroupEntity();
           newGroup.setGroupName(request.getGroupName());
           groupRepository.save(newGroup);
           for (String name : request.getUserNames()) 
           {
                User user = userRepository.findByName(name)
                    .orElseThrow(() -> new IllegalStateException("User not found with name: " + name));
                
                //add user to the group
                newGroup.getUsers().add(user); 
                
                
                user.getGroups().add(newGroup);
                
           }
           
           return groupRepository.save(newGroup);
      }
}
package com.backend.backend_server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_server.data_transfer_objects.UserInfo;
import com.backend.backend_server.repository.UserRepository;

@Service
public class UserService 
{

     @Autowired
     private UserRepository userRepository;

     /**
     * Finds all users who are part of a specific group.
     * @param groupName The name of the group to search for.
     * @return A list of UserDTOs.
     */
     public List<UserInfo> findUsersByGroup(String groupName) 
     {
         // This is a simple implementation. In a real app with a proper Group entity,
         // this query would be more direct.
         return userRepository.findAll().stream()
                .filter(user -> user.getGroupsalloted() != null && user.getGroupsalloted().contains(groupName))
                .map(UserInfo::new) // Convert each User to a UserDTO
                .collect(Collectors.toList());
     }
}

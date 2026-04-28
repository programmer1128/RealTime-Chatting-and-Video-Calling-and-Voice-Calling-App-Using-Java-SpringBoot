/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.service;

import java.util.List;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.backend.backend_server.entity.Role;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;
import com.backend.backend_server.data_transfer_objects.UserInfo;

@Service
public class UserService implements UserDetailsService 
{

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private AuditService auditService;

     public List<User> findAllUsers() 
     {
         return userRepository.findAll();
     }

     public List<User> findAllRegularUsers() 
     {
         return userRepository.findByRole(Role.USER);
     }

     public User findUserById(Long id) 
     {
         return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
     }

     public User updateStatus(Long id, String newStatus) 
     {
         User user = findUserById(id);
         user.setStatus(newStatus);
         if ("APPROVED".equals(newStatus)) 
         {
             user.setPersonnelId(generatePersonnelID(user.getUsername()));
         }
         auditService.logAction("STATUS_CHANGED", "ADMIN", user, "Status changed to " + newStatus);
         return userRepository.save(user);
     }

     public User findUserByUsername(String username) 
     {
         return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
     }

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
     {
         return findUserByUsername(username);
     }

     public List<UserInfo> findUsersByGroup(String groupName) 
     {
         return userRepository.findAll().stream()
                .filter(user -> user.getGroupsalloted() != null && user.getGroupsalloted().contains(groupName))
                .map(UserInfo::new)
                .collect(Collectors.toList());
     }

     private String generatePersonnelID(String username) 
     {
         return Base64.getEncoder().encodeToString(username.getBytes());
     }
}

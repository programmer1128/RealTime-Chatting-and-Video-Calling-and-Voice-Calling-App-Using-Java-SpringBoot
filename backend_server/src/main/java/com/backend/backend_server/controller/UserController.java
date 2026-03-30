package com.backend.backend_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.data_transfer_objects.UserInfo;
import com.backend.backend_server.service.UserService;

/**
 * Controller for handling all User-related API endpoints.
 */
@RestController
@RequestMapping("/api/users") // A dedicated base path for user actions
public class UserController 
{

     // This controller will need its own UserService
     @Autowired
     private UserService userService;

     /**
     * Finds all users who are part of a specific group.
     * @param groupName The name of the group.
     * @return A list of users in that group.
     */
     @GetMapping("/by-group/{groupName}")
     public ResponseEntity<List<UserInfo>> getUsersByGroup(@PathVariable String groupName) 
     {
         List<UserInfo> users = userService.findUsersByGroup(groupName);
         return ResponseEntity.ok(users);
     }
}


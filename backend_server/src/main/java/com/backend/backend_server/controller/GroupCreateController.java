/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.data_transfer_objects.GroupCreateRequest;
import com.backend.backend_server.service.GroupCreateService;

@RestController
@RequestMapping("/users")
public class GroupCreateController 
{

     @Autowired
     GroupCreateService groupCreateService;

     @PostMapping("/create-a-group")
     public ResponseEntity<?> createGroup(@RequestBody GroupCreateRequest request)
     {
         //call service for group creation
         System.out.println("--- RECEIVED REQUEST --- groupName: '" + request.getGroupName() + "', usernames: " + request.getUserNames());
         groupCreateService.createGroup(request);
         return ResponseEntity.ok("GroupCreated");
     }
}

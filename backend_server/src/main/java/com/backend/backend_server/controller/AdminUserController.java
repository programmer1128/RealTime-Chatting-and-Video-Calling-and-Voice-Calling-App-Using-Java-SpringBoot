package com.backend.backend_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.entity.User;
import com.backend.backend_server.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> viewAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}/status")
    public ResponseEntity<User> getStatus(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<User> updateStatus(@PathVariable Long id, @RequestParam String status) {
        User updatedUser = userService.updateStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }
}
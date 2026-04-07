package com.backend.backend_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) 
    {
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone number already registered");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
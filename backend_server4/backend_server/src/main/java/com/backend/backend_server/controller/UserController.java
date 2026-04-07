package com.backend.backend_server.controller;

import com.backend.backend_server.entity.User;
import com.backend.backend_server.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/all/{id}")
    public List<User> getAllUsersExcept(@PathVariable Long id) {
        return userService.getAllUsersExcept(id);
    }
}

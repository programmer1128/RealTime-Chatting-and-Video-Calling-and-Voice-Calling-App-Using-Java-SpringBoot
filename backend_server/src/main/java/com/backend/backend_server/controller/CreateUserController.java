package com.backend.backend_server.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.backend_server.data_transfer_objects.CreateUserRequest;
import com.backend.backend_server.service.CreateUserService;

@RestController
@RequestMapping("/admin/create-user")
public class CreateUserController {

    @Autowired
    private CreateUserService createUserSerivce;

    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> createNewUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(createUserSerivce.createUser(request));
    }
}

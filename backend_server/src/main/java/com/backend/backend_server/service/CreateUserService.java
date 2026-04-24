package com.backend.backend_server.service;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.backend_server.data_transfer_objects.CreateUserRequest;
import com.backend.backend_server.entity.Role;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;

@Service
public class CreateUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Map<String, String> createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return Map.of("message", "Email already exists");
        }
        if (userRepository.existsByPersonnelId(request.getPersonelid())) {
            return Map.of("message", "Personnel ID already exists");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setFullname(request.getFullname());
        newUser.setGroupsalloted(request.getGroupsalloted());
        newUser.setRole(Role.valueOf(request.getRole()));
        newUser.setPersonnelId(request.getPersonelid());
        newUser.setUsername(request.getUsername());

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        newUser.setOtp(otp);
        newUser.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

        userRepository.save(newUser);
        emailService.sendEmail(newUser.getEmail(), otp);

        return Map.of("message", "User created successfully");
    }
}

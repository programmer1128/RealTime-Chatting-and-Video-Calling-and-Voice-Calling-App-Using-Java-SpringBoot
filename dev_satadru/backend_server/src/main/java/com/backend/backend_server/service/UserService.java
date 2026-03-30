package com.backend.backend_server.service;

import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User registerUser(User user) {
        Optional<User> existing = userRepository.findByNameAndMobileNumber(user.getName(), user.getMobileNumber());
        return existing.orElseGet(() -> userRepository.save(user));
    }

    public List<User> getAllUsersExcept(Long id) {
        return userRepository.findAll().stream()
                .filter(u -> !u.getId().equals(id))
                .toList();
    }
}

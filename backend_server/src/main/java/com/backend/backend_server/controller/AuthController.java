package com.backend.backend_server.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.backend.backend_server.data_transfer_objects.LoginRequest;
import com.backend.backend_server.data_transfer_objects.LoginResponse;
import com.backend.backend_server.data_transfer_objects.UserVerifyRequest;
import com.backend.backend_server.dto.AuthRequestDTO;
import com.backend.backend_server.dto.AuthResponseDTO;
import com.backend.backend_server.dto.BeforeLoginDTO;
import com.backend.backend_server.dto.UserDTO;
import com.backend.backend_server.dto.UserLoginDTO;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.service.AppLoginService;
import com.backend.backend_server.service.AuthService;
import com.backend.backend_server.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AppLoginService appLoginService;

    @Autowired
    private UserService userService;

    // --- Original Auth Endpoints ---

    @PostMapping("/user/verify")
    public ResponseEntity<?> verifyUser(@RequestBody UserVerifyRequest request) {
        try {
            User user = authService.validateLogin(request);
            return ResponseEntity.ok(convertToLoginResponse(user));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/old-login")
    public ResponseEntity<?> oldLogin(@RequestBody LoginRequest request) {
        User user = appLoginService.validatePassword(request);
        if (user != null) {
            return ResponseEntity.ok(convertToLoginResponse(user));
        }
        return new ResponseEntity<>(Map.of("message", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
    }

    // --- New JWT/OTP Auth Endpoints ---

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequestDTO request) {
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<BeforeLoginDTO> login(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/otp-auth")
    public ResponseEntity<UserDTO> otpAuthenticate(@RequestBody UserLoginDTO request) {
        return ResponseEntity.ok(authService.issueAccessToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        authService.logoutUser(jwt);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            return ResponseEntity.ok(userService.findUserByUsername(username));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private LoginResponse convertToLoginResponse(User user) {
        LoginResponse response = new LoginResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setFullname(user.getFullname());
        response.setGroupsAlloted(user.getGroupsalloted());
        response.setId(user.getId());
        response.setPersonelid(user.getPersonnelId());
        response.setRole(user.getRole() != null ? user.getRole().name() : null);
        return response;
    }
}

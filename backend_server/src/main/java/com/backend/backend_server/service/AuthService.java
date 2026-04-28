/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.backend_server.config.JwtTokenProvider;
import com.backend.backend_server.data_transfer_objects.UserVerifyRequest;
import com.backend.backend_server.dto.AuthRequestDTO;
import com.backend.backend_server.dto.BeforeLoginDTO;
import com.backend.backend_server.dto.UserDTO;
import com.backend.backend_server.dto.UserLoginDTO;
import com.backend.backend_server.entity.OTP;
import com.backend.backend_server.entity.Role;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.OTPRepository;
import com.backend.backend_server.repository.UserRepository;
import com.backend.backend_server.util.EncryptionUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuditService auditService;

    @Autowired
    private EmailService emailService;

    public User validateLogin(UserVerifyRequest request) {
        User loginUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        if (request.getOtp() == null || !(request.getOtp().equals(loginUser.getOtp()))) {
            throw new RuntimeException("Error: OTP is incorrect");
        } else if (LocalDateTime.now().isAfter(loginUser.getOtpExpiry())) {
            throw new RuntimeException("Error: OTP is expired");
        }

        loginUser.setPassword(passwordEncoder.encode(request.getPassword()));
        loginUser.setOtp(null);
        loginUser.setOtpExpiry(null);
        userRepository.save(loginUser);
        return loginUser;
    }

    public User registerUser(AuthRequestDTO request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already taken.");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(encryptionUtil.hashPassword(request.password()));
        user.setRole(Role.USER);
        user.setStatus("PENDING");
        
        User registeredUser = userRepository.save(user);
        auditService.logAction("USER_REGISTERED", user.getRole().toString(), registeredUser, "Account created, status PENDING.");
        return registeredUser;
    }

    public BeforeLoginDTO loginUser(AuthRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Invalid username or password."));

        if (!encryptionUtil.verifyPassword(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }
        
        if (!"APPROVED".equals(user.getStatus()) && Role.ADMIN != user.getRole()) {
            throw new RuntimeException("Account status is " + user.getStatus() + ". Awaiting HQ approval.");
        }

        int otpValue = generateOTP();
        OTP otpObj = new OTP();
        otpObj.setId(user.getId());
        otpObj.setExpiry(LocalDateTime.now().plusMinutes(10));
        otpObj.setOtp(otpValue);
        otpObj.setUsername(user.getUsername());
        otpRepository.save(otpObj);

        emailService.sendEmail(user.getEmail(), Integer.toString(otpValue));

        return new BeforeLoginDTO(user.getId(), user.getUsername(), user.getRole(), user.getStatus());
    }

    public UserDTO issueAccessToken(UserLoginDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("No username found"));
            
        OTP otpObj = otpRepository.findOTPById(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP doesn't exist!"));
        
        if (otpObj.getOtp() != request.otp()) {
            throw new RuntimeException("OTP doesn't match!");
        } else if (LocalDateTime.now().isAfter(otpObj.getExpiry())) {
            throw new RuntimeException("OTP has expired!");
        }
        
        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);
        
        UserDTO response = new UserDTO(user.getUsername(), user.getRole(), user.getPersonnelId(), accessToken, refreshToken);
        auditService.logAction("USER_LOGIN", user.getRole().toString(), user, "Successful login.");
        otpRepository.deleteOTPById(otpObj.getId());
        return response;
    }

    public void logoutUser(String token) {
        // mock
    }

    private int generateOTP() {
        return (int) (Math.random() * 900000) + 100000;
    }
}

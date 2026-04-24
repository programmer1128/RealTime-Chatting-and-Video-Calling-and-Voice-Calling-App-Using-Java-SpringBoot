package com.backend.backend_server.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
public class OTP {
    @Id
    private Long id;

    private String username;
    private int otp;
    private LocalDateTime expiry;

    public OTP() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getOtp() { return otp; }
    public void setOtp(int otp) { this.otp = otp; }

    public LocalDateTime getExpiry() { return expiry; }
    public void setExpiry(LocalDateTime expiry) { this.expiry = expiry; }
}

/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.backend_server.entity.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    default OTP deleteOTPById(Long id) {
        Optional<OTP> otp = findById(id);
        otp.ifPresent(this::delete);
        return otp.orElse(null);
    }

    default List<OTP> findAllOTPs() {
        return findAll();
    }

    default Optional<OTP> findOTPByUsername(String username) {
        return findByUsername(username);
    }

    Optional<OTP> findByUsername(String username);

    default Optional<OTP> findOTPById(Long id) {
        return findById(id);
    }
}

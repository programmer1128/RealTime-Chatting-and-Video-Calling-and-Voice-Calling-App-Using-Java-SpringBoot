package com.backend.backend_server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_server.entity.AuditLog;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.AuditLogRepository;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(String action, String performedByRole, User user, String details) {
        AuditLog log = new AuditLog();
        log.setTimestamp(LocalDateTime.now());
        log.setAction(action);
        log.setPerformedByRole(performedByRole);
        log.setUser(user);
        log.setDetails(details);
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAllAuditLogs() {
        // TODO: Add filtering, sorting, and pagination logic
        return auditLogRepository.findAll();
    }
}
package com.backend.backend_server.controller;

import com.backend.backend_server.entity.AuditLog;
import com.backend.backend_server.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/admin/audit-logs")
public class AdminAuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> viewAuditLogs() {
        List<AuditLog> logs = auditService.getAllAuditLogs();
        return ResponseEntity.ok(logs);
    }
}
package com.backend.backend_server.controller;

import com.backend.backend_server.dto.VPNDTO;
import com.backend.backend_server.entity.VPNSession;
import com.backend.backend_server.service.VPNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/vpn")
public class AdminVPNController {

    @Autowired
    private VPNService vpnService;

    @GetMapping("/sessions")
    public ResponseEntity<List<VPNSession>> getActiveSessions() {
        return ResponseEntity.ok(vpnService.getAllActiveSessions());
    }

    @PostMapping("/terminate/{id}")
    public ResponseEntity<Void> terminateSession(@PathVariable Long id) {
        vpnService.terminateSession(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restart/{id}")
    public ResponseEntity<Void> restartSession(@PathVariable Long id) {
        vpnService.restartSession(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<VPNSession> createSession(@RequestBody VPNDTO vpnDto) {
        return ResponseEntity.ok(vpnService.createSession(vpnDto.userId(), vpnDto.clientIp(), vpnDto.gatewayIp()));
    }
}

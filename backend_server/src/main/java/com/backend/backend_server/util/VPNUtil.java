package com.backend.backend_server.util;

import org.springframework.stereotype.Component;

@Component
public class VPNUtil {
    public String generateSessionId() {
        return "mock-vpn-session-" + System.currentTimeMillis();
    }
    public void restartSession(String sessionId) {
        // mock
    }
}

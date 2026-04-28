/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

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

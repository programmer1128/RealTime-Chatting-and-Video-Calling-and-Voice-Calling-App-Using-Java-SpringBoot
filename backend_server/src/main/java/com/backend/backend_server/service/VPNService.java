package com.backend.backend_server.service;

import com.backend.backend_server.entity.User;
import com.backend.backend_server.entity.VPNSession;
import com.backend.backend_server.repository.VPNSessionRepository;
import com.backend.backend_server.repository.UserRepository;
import com.backend.backend_server.util.VPNUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VPNService 
{

     @Autowired
     private VPNSessionRepository vpnSessionRepository;

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private VPNUtil vpnUtil;

     @Autowired
     private AuditService auditService;

     public List<VPNSession> getAllActiveSessions() 
     {
         return vpnSessionRepository.findAll();
     }
 
     public void terminateSession(Long id) 
     {
         VPNSession session = vpnSessionRepository.findById(id).orElseThrow();
         session.setStatus("TERMINATED");
         vpnSessionRepository.save(session);
         auditService.logAction("VPN_TERMINATED", "ADMIN", session.getUser(), "Session " + session.getSessionId() + " terminated.");
     }

     public void restartSession(Long id) 
     {
         VPNSession session = vpnSessionRepository.findById(id).orElseThrow();
         vpnUtil.restartSession(session.getSessionId());
         session.setStatus("RESTARTED");
         session.setLastActive(LocalDateTime.now());
         vpnSessionRepository.save(session);
         auditService.logAction("VPN_RESTARTED", "ADMIN", session.getUser(), "Session " + session.getSessionId() + " restarted.");
     }

     public VPNSession createSession(Long userId, String clientIp, String gatewayIp) 
     {
         User user = userRepository.findById(userId).orElseThrow();
         VPNSession session = new VPNSession();
         session.setSessionId(vpnUtil.generateSessionId());
         session.setUser(user);
         session.setClientIp(clientIp);
         session.setGatewayIp(gatewayIp);
         session.setStatus("ACTIVE");
         session.setStartTime(LocalDateTime.now());
         session.setLastActive(LocalDateTime.now());
         return vpnSessionRepository.save(session);
     }
}

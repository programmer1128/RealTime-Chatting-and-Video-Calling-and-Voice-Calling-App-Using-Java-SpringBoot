package com.backend.backend_server.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import 
org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component 
public class VideoCallWebSocketHandler extends TextWebSocketHandler 
{

     private static final Logger logger = LoggerFactory.getLogger(VideoCallWebSocketHandler.class);
     private final ObjectMapper objectMapper = new ObjectMapper();
     private final Map<String, Map<String, WebSocketSession>> callRooms = new ConcurrentHashMap<>();
     private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
     private final Map<String, String[]> sessionToUserAndCall = new ConcurrentHashMap<>();

     @Override
     public void afterConnectionEstablished(WebSocketSession session) 
     {
         logger.info("New WebSocket connection established: {}", session.getId());
     }

     @Override
     protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception 
     {
         try 
         {
             Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
             String type = (String) payload.get("type");
             String userId = String.valueOf(payload.get("userId"));
             String callId = (String) payload.get("callId");

             // Logic for the persistent notification channel
             if (callId != null && callId.startsWith("user-channel-")) 
             {
                 userSessions.put(userId, session);
                 sessionToUserAndCall.put(session.getId(), new String[]{userId, callId});
                 logger.info("User {} registered for notifications.", userId);
                 return;
             }

             // Logic for the temporary call signaling channel
             switch (type) 
             {
                 case "join_call":
                     handleJoinCall(session, userId, callId);
                     break;
                 case "offer":
                 case "answer":
                 case "ice_candidate":
                     handleSignalingMessage(payload, message);
                     break;
                 case "hangup": //  NEW: Handle hangup messages
                     handleHangup(payload);
                     break;
                 default:
                     logger.warn("Unknown message type received: {}", type);
             }
         } 
         catch (Exception e) 
         {
             logger.error("Error processing message", e);
         }
     }

     private void handleJoinCall(WebSocketSession session, String userId, String callId) 
     {
         Map<String, WebSocketSession> room = callRooms.computeIfAbsent(callId, k -> new ConcurrentHashMap<>());
        
         //NEW: Announce the new user to everyone already in the room
         String announcement = String.format("{\"type\":\"user_joined\",\"userId\":\"%s\"}", userId);
         room.values().stream().filter(WebSocketSession::isOpen).forEach(sess -> 
         {
             try 
             {
                 sess.sendMessage(new TextMessage(announcement));
             }
             catch (IOException e) 
             {
                 logger.error("Failed to send user_joined announcement", e);
             }
         });

         room.put(userId, session);
         sessionToUserAndCall.put(session.getId(), new String[]{userId, callId});
         logger.info("User {} joined call {}. Room size: {}", userId, callId, room.size());
     }
    
     // NEW: Broadcast hangup messages
     private void handleHangup(Map<String, Object> payload) 
     {
         String callId = (String) payload.get("callId");
         String userId = String.valueOf(payload.get("userId"));
         Map<String, WebSocketSession> room = callRooms.get(callId);
         if (room != null) 
         {
             String hangupMessage = String.format("{\"type\":\"hangup\",\"userId\":\"%s\"}", userId);
             room.values().forEach(sess -> {
                 try 
                 {
                     sess.sendMessage(new TextMessage(hangupMessage));
                 } 
                 catch (IOException e) 
                 { /* ignore */ }
             });
         }
     }
 
     @Override
     public void afterConnectionClosed(WebSocketSession session, CloseStatus status) 
     {
         String[] userAndCall = sessionToUserAndCall.remove(session.getId());
         if (userAndCall != null) 
         {
             String userId = userAndCall[0];
             String callId = userAndCall[1];
            
             // If it was a notification channel, just remove them from userSessions
             if (callId.startsWith("user-channel-")) 
             {
                 userSessions.remove(userId);
                 logger.info("User {} unregistered from notifications.", userId);
             } 
             else 
             {
                 // If it was a call channel, clean up the room
                 Map<String, WebSocketSession> room = callRooms.get(callId);
                 if (room != null) 
                 {
                     room.remove(userId);
                     logger.info("User {} left call {}.", userId, callId);
                     if (room.isEmpty()) callRooms.remove(callId);
                 }
             }
         }
     }

     public void sendMessageToUser(Long userId, Map<String, Object> payload) 
     {
         String userIdStr = String.valueOf(userId);
         WebSocketSession session = userSessions.get(userIdStr);

         if (session != null && session.isOpen()) 
         {
             try 
             {
                 String message = objectMapper.writeValueAsString(payload);
                 session.sendMessage(new TextMessage(message));
                 logger.info("Sent direct message to user {}: {}", userId, message);
             } 
             catch (JsonProcessingException e) 
             {
                 logger.error("Error serializing message payload for user {}", userId, e);
             } 
             catch (IOException e) 
             {
                 logger.error("Error sending message to user {}", userId, e);
             }
         } 
         else 
         {
             logger.warn("Could not find an active WebSocket session for user ID: {}", userId);
         }
     }
    
     private void handleSignalingMessage(Map<String, Object> payload, TextMessage message) throws IOException 
     {
         String callId = (String) payload.get("callId");
         String target = String.valueOf(payload.get("target"));
         Map<String, WebSocketSession> room = callRooms.get(callId);

         if (room != null) 
         {
             WebSocketSession targetSession = room.get(target);
             if (targetSession != null && targetSession.isOpen()) 
             {
                 targetSession.sendMessage(message);
             } 
             else 
             {
                 logger.warn("Target user {} not found or session closed in call {}", target, callId);
             }
         } 
         else 
         {
             logger.warn("Call room not found for callId: {}", callId);
         }
     }
}


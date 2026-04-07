package com.backend.backend_server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class VideoCallWebSocketHandler extends TextWebSocketHandler 
{

     private static final Logger logger = LoggerFactory.getLogger(VideoCallWebSocketHandler.class);
     private final ObjectMapper objectMapper = new ObjectMapper();

     // The main data structure to hold call rooms.
     // Key: callId, Value: A map of participants in that call (Key: userId, Value: WebSocketSession)
     private final Map<String, Map<String, WebSocketSession>> callRooms = new ConcurrentHashMap<>();

     // A helper map to quickly find which call a session belongs to on disconnect.
     // Key: sessionId, Value: An array containing [userId, callId]
     private final Map<String, String[]> sessionToUserAndCall = new ConcurrentHashMap<>();

     @Override
     public void afterConnectionEstablished(WebSocketSession session) 
     {
         logger.info("New connection established: {}", session.getId());
     }

     @Override
     protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception 
     {
         try 
         {
             Map<String, String> payload = objectMapper.readValue(message.getPayload(), Map.class);
             String type = payload.get("type");
             String userId = payload.get("userId");
             String callId = payload.get("callId");

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

                 case "leave_call":
                     handleLeaveCall(session);
                     break;

                 default:
                     logger.warn("Unknown message type received: {}", type);
                     break;
             }

         } 
         catch (IOException e) 
         {
             logger.error("Error processing message: {}", e.getMessage());
         }
     }

     @Override
     public void afterConnectionClosed(WebSocketSession session, CloseStatus status) 
     {
         handleLeaveCall(session);
         logger.info("Connection closed: {} with status: {}", session.getId(), status);
     }

     // In service/VideoCallWebSocketHandler.java

     private void handleJoinCall(WebSocketSession session, String userId, String callId) 
     {
         logger.info("User {} attempting to join call {}", userId, callId);
         Map<String, WebSocketSession> room = callRooms.computeIfAbsent(callId, k -> new ConcurrentHashMap<>());

         // --- NEW LOGIC: Announce the new user to existing participants ---
         // First, get a list of everyone who is already here.
         List<String> existingUserIds = new ArrayList<>(room.keySet());

         // Create the announcement message for the new user.
         String newUserAnnouncement = String.format("{\"type\":\"user_joined\",\"userId\":\"%s\"}", userId);

         // Send the announcement to everyone already in the room.
         room.values().forEach(sess -> {
             try 
             {
                 if (sess.isOpen()) 
                 {
                     sess.sendMessage(new TextMessage(newUserAnnouncement));
                 }
             } 
             catch (IOException e) 
             {
                 logger.error("Failed to send user_joined announcement", e);
             }
         });

         // --- NEW LOGIC: Tell the new user who is already in the room ---
         try 
         {
             Map<String, Object> roomStateMessage = new HashMap<>();
             roomStateMessage.put("type", "room_state");
             roomStateMessage.put("users", existingUserIds);
             String roomStatePayload = objectMapper.writeValueAsString(roomStateMessage);
             session.sendMessage(new TextMessage(roomStatePayload));
         } 
         catch (IOException e) 
         {
             logger.error("Failed to send room_state message to new user", e);
         }

         // --- Now, add the new user to the room ---
         room.put(userId, session);
         sessionToUserAndCall.put(session.getId(), new String[]{userId, callId});
         logger.info("User {} joined call {}. Room size is now {}", userId, callId, room.size());
     }

     private void handleSignalingMessage(Map<String, String> payload, TextMessage message) throws IOException 
     {
         String callId = payload.get("callId");
         String target = payload.get("target");

         Map<String, WebSocketSession> room = callRooms.get(callId);

         if (room != null) 
         {
             WebSocketSession targetSession = room.get(target);
             if (targetSession != null && targetSession.isOpen()) 
             {
                 logger.info("Forwarding message to target {} in call {}", target, callId);
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

     private void handleLeaveCall(WebSocketSession session) 
     {
         String[] userAndCall = sessionToUserAndCall.remove(session.getId());
         if (userAndCall != null) 
         {
             String userId = userAndCall[0];
             String callId = userAndCall[1];
            
             Map<String, WebSocketSession> room = callRooms.get(callId);
             if (room != null) 
             {
                 room.remove(userId);
                 logger.info("User {} has left call {}", userId, callId);
                
                 // If the room is now empty, you can remove it to save memory.
                 if (room.isEmpty()) 
                 {
                     callRooms.remove(callId);
                     logger.info("Call room {} is now empty and has been removed.", callId);
                 }
             }
         }
     }
}
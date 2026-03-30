package com.backend.backend_server.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Handles WebRTC signaling messages between peers via WebSocket
 */
public class WebRTCSignalingHandler extends TextWebSocketHandler {

    // Map to hold sessions by username (phone number treated as user id here)
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Expect client to send a "register" message with its username immediately after connecting
        System.out.println("WS Connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JsonNode json = mapper.readTree(payload);

        // Basic message format: { "type": "register" / "offer" / "answer" / "candidate", "from": "userX", "to": "userY", "data": {...} }
        String type = json.get("type").asText();

        switch (type) {
            case "register":
                handleRegister(session, json);
                break;
            case "offer":
            case "answer":
            case "candidate":
                handleRelay(session, json);
                break;
            default:
                System.out.println("Unknown message type: " + type);
        }
    }

    private void handleRegister(WebSocketSession session, JsonNode json) throws Exception {
        String username = json.get("from").asText();
        sessions.put(username, session);
        System.out.println("Registered user for signaling: " + username);

        // Send ack
        session.sendMessage(new TextMessage("{\"type\":\"register\",\"status\":\"success\"}"));
    }

    private void handleRelay(WebSocketSession session, JsonNode json) throws Exception {
        String toUser = json.get("to").asText();
        WebSocketSession receiverSession = sessions.get(toUser);

        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(json.toString()));
            System.out.println("Relayed " + json.get("type").asText() + " message from " + json.get("from").asText() + " to " + toUser);
        } else {
            System.out.println("User " + toUser + " not connected for signaling.");
            // Optionally send error back to sender
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().removeIf(s -> s.equals(session));
        System.out.println("WS Connection closed: " + session.getId());
    }
}

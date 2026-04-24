# Real-Time Chat & Video Calling Application

An integrated, enterprise-ready Spring Boot backend providing robust services for real-time voice/video calls, secure messaging, and comprehensive administrative controls. This project consolidates four separate modules into a single, high-performance service.

## 🚀 Key Features

-   **📽️ Video & Voice Calling**: Real-time WebRTC signaling using low-level WebSockets for high-performance peer-to-peer communication.
-   **💬 Real-Time Messaging**: Support for both Direct (1-on-1) and Group chats using STOMP over WebSockets.
-   **🔐 Advanced Authentication**: Dual authentication flow with JWT-based sessions and OTP (One-Time Password) verification via email.
-   **🛡️ HQ Command Dashboard (Admin)**:
    *   **Audit Logs**: Comprehensive tracking of user and admin actions.
    *   **VPN Management**: Real-time monitoring, creation, and termination of secure VPN sessions.
    *   **User Control**: Admin approval flow for new registrations and role management.
-   **📦 Unified Architecture**: A clean, non-redundant codebase with shared entities and repositories for Users, Groups, and Messages.

---

## 🛠️ Core Components & Code Snippets

### 1. Unified WebSocket Configuration
We use a single configuration to manage both low-level signaling (WebRTC) and high-level messaging (STOMP).

```java
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer 
{

     // For Low-level WebSocket (Video Call signaling)
     @Override
     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) 
     {
         registry.addHandler(videoCallWebSocketHandler, "/video-call").setAllowedOrigin("*");
     }

     // For STOMP WebSocket (Chat)
     @Override
     public void configureMessageBroker(MessageBrokerRegistry config) 
     {
         config.enableSimpleBroker("/topic");
         config.setApplicationDestinationPrefixes("/app");
     }
}
```

### 2. Real-Time Message Handling
The `MessageController` handles both RESTful direct messaging and live WebSocket broadcasts for groups.

```java
// Handle live group messages via WebSocket
@MessageMapping("/group-chat/{groupId}")
@SendTo("/topic/group/{groupId}")
public Message handleGroupMessage(@Payload Message message, @DestinationVariable String groupId) 
{
     message.setGroupId(groupId);
     return messageService.saveMessage(message);
}

// REST endpoint for direct messaging history
@GetMapping("/chat")
public ResponseEntity<List<Message>> getChat(@RequestParam Long user1, @RequestParam Long user2) 
{
     return ResponseEntity.ok(messageService.getChat(user1, user2));
}
```

### 3. WebRTC Signaling Handler
Manages "Call Rooms" and ICE candidate exchanges between peers.

```java
private void handleJoinCall(WebSocketSession session, String userId, String callId) 
{
     Map<String, WebSocketSession> room = callRooms.computeIfAbsent(callId, k -> new ConcurrentHashMap<>());
    
     // Announce new joiner to existing participants
     String announcement = String.format("{\"type\":\"user_joined\",\"userId\":\"%s\"}", userId);
     room.values().stream().filter(WebSocketSession::isOpen).forEach(sess -> {
        sess.sendMessage(new TextMessage(announcement));
     });
     room.put(userId, session);
}
```

### 4. Admin VPN Management
Allows HQ admins to monitor and control active secure tunnels.

```java
@PostMapping("/terminate/{id}")
public ResponseEntity<Void> terminateSession(@PathVariable Long id) 
{
     vpnService.terminateSession(id);
     return ResponseEntity.ok().build();
}
```

---

## 📂 Project Structure

```text
backend_server/
├── src/main/java/com/backend/backend_server/
│   ├── config/             # Security, JWT, & WebSocket Configs
│   ├── controller/         # Auth, Messaging, Calling, and Admin APIs
│   ├── entity/             # JPA Entities (User, Group, Message, AuditLog, etc.)
│   ├── repository/         # Spring Data JPA Repositories
│   ├── service/            # Core business logic & signaling handlers
│   └── dto/                # Data Transfer Objects for API requests/responses
└── src/main/resources/
    └── application.properties # Database & Mail server settings
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 21+
- Maven 3.6+
- MySQL (configured in `application.properties`)

### Installation Steps

1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/yourusername/RealTimeChattingandVideoCallingApp.git
    cd RealTimeChattingandVideoCallingApp/backend_server
    ```

2.  **Configure Database**:
    Update `src/main/resources/application.properties` with your MySQL credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/your_db
    spring.datasource.username=your_user
    spring.datasource.password=your_password
    ```

3.  **Build and Run**:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

The server will start on `http://localhost:8080`.

---

## 🧪 Testing
The project includes automated context loading tests. Run them using:
```bash
./mvnw test
```


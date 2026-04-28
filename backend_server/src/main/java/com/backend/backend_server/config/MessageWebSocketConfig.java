/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/**
 * Configuration class for setting up the WebSocket message broker for the chat feature.
 * This uses STOMP (Simple Text Oriented Messaging Protocol) over WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker // This enables the message broker.
public class MessageWebSocketConfig implements WebSocketMessageBrokerConfigurer 
{

    /**
     * This method configures the message broker, which is responsible for routing
     * messages from one client to another.
     */
     @Override
     public void configureMessageBroker(MessageBrokerRegistry registry) 
     {
         // This sets up a simple, in-memory message broker that will broadcast messages
         // to clients subscribed to destinations prefixed with "/topic".
         // This is our "public bulletin board".
         registry.enableSimpleBroker("/topic");

         // This defines the prefix for messages that are bound for methods annotated
         // with @MessageMapping in a controller. This is our "server's inbox".
         registry.setApplicationDestinationPrefixes("/app");
     }

     /**
     * This method registers the STOMP endpoint, which is the URL that clients
     * will use to connect to the WebSocket server.
     */
     @Override
     public void registerStompEndpoints(StompEndpointRegistry registry) 
     {
         // This registers the "/ws-chat" endpoint. The React Native app will connect to this URL.
         // .withSockJS() is a fallback for clients that don't support modern WebSockets.
         // .setAllowedOrigins("*") allows connections from any origin, which is needed for development.
         registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); 
     }
}

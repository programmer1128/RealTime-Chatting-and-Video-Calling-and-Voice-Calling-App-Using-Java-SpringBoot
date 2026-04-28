/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

  package com.backend.backend_server.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired; // ✅ Using the correct, consistent DTO name
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.data_transfer_objects.CallResponse;
import com.backend.backend_server.data_transfer_objects.GroupCallRequest;
import com.backend.backend_server.service.CallService;

/**
 * Controller for handling all Call-related API endpoints.
 */
@RestController
@RequestMapping("/api/calls")
public class CallController 
{

    @Autowired
    private CallService callService;

    /**
     * Creates a new group call session and invites the specified participants.
     * @param request The request body containing the initiator and participant IDs.
     * @return A CallResponse DTO with the details of the new call.
     */
    
     @PostMapping("/group")
   
     public ResponseEntity<CallResponse> createGroupCall(@RequestBody GroupCallRequest request) 
     {
         CallResponse callResponse = callService.createGroupCall(request);
         return new ResponseEntity<>(callResponse, HttpStatus.CREATED);
     }

    /**
     * Ends an existing call session.
     * @param callId The UUID of the call to end.
     * @return A confirmation message.
     */
     @PostMapping("/end/{callId}")
     public ResponseEntity<String> endCall(@PathVariable UUID callId) 
     {
         callService.endCall(callId);
         return ResponseEntity.ok("Call session ended successfully.");
     }

    // NOTE: The getUsersByGroup logic has been correctly moved to the UserController.
}


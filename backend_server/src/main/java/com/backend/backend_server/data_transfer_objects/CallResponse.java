package com.backend.backend_server.data_transfer_objects;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for responding to a call creation request.
 * This defines the JSON structure the server sends back to the client.
 */
public class CallResponse 
{

     // RENAMED: Changed from 'sessionId' to 'id' to match the CallSession entity.
     private UUID id;
     private String sfuRoomId;
     private String encryptionKeyId;
     private Instant createdAt;
     private String status;

     //UPDATED: Constructor now uses the 'id' field.
     public CallResponse(UUID id, String sfuRoomId, String encryptionKeyId, Instant createdAt, String status) 
     {
         this.id = id;
         this.sfuRoomId = sfuRoomId;
         this.encryptionKeyId = encryptionKeyId;
         this.createdAt = createdAt;
         this.status = status;
     }

    // --- Getters ---

     public UUID getId() 
     {
         return id;
     }

     public String getSfuRoomId() 
     {
         return sfuRoomId;
     }

     public String getEncryptionKeyId() 
     {
         return encryptionKeyId;
     }

     public Instant getCreatedAt() 
     {
         return createdAt;
     }

     public String getStatus() 
     {
         return status;
     }
}


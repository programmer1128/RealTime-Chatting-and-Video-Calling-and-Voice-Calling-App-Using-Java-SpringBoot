/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;
import java.util.UUID;

public class CallCreateRequest 
{
     private String groupId;
     private UUID initiatorId;
     private Integer maxParticipants;

     // --- Getters and Setters ---
     public String getGroupId() 
     { 
         return groupId; 
     }

     public void setGroupId(String groupId) 
     { 
         this.groupId = groupId; 
     }

     public UUID getInitiatorId() 
     { 
         return initiatorId; 
     }
     public void setInitiatorId(UUID initiatorId) 
     { 
         this.initiatorId = initiatorId; 
     }

     public Integer getMaxParticipants() 
     { 
         return maxParticipants; 
     }
     
     public void setMaxParticipants(Integer maxParticipants) 
     {  
         this.maxParticipants = maxParticipants; 
     }
}
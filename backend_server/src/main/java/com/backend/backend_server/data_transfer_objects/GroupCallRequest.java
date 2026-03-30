package com.backend.backend_server.data_transfer_objects;

import java.util.List;

public class GroupCallRequest 
{
     //this is the data transfer object for the group call request json. The json will contain the initiator
     //id and the list of users that will join the call
     private String callType;

     private Long initiatorId;

     private List<Long> participantIds;

     //setter for the initiatorId

     public void setInitiatorId(Long initiatorId)
     {
         this.initiatorId=initiatorId;
     }

     //getter for the initiatorId
     public Long getInitiatorId()
     {
         return this.initiatorId;
     }

     //setter for the participants list
     public void setParticipantIds(List<Long> participantIds)
     {
         this.participantIds=participantIds;
     }

     //getter for the participants list
     public List<Long> getParticipantIds()
     {
         return this.participantIds;
     }

     //setter for the callType
     public void setCallType(String callType)
     {
         this.callType=callType;
     }

     //getter for the call Type
     public String getCallType()
     {
         return this.callType;
     }
}

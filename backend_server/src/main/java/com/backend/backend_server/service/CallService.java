package com.backend.backend_server.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend_server.data_transfer_objects.CallResponse;
import com.backend.backend_server.data_transfer_objects.GroupCallRequest;
import com.backend.backend_server.data_transfer_objects.UserInfo;
import com.backend.backend_server.entity.CallSession;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.CallParticipantRepository;
import com.backend.backend_server.repository.CallSessionRepository;
import com.backend.backend_server.repository.UserRepository;

@Service
public class CallService 
{
     @Autowired
     private CallSessionRepository callSessionRepository;

     @Autowired
     private CallParticipantRepository callParticipantRepository;

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private VideoCallWebSocketHandler videoCallWebSocketHandler;

     //now we will define the methods to serve our call service

     //now this method will take the groupcall request data transfer object class object as the 
     //paramater. It will contain the initiator id and the list of participants who will join the 
     //call

     //transactional annotation helps to maintain data consisitency. This will tell springboot
     //that either all the data inside this method will succeed to get into the database or
     //it will not
     @Transactional
     public CallResponse createGroupCall(GroupCallRequest request)
     {
         //now at the very beginning we need to check if the user who is initiating a 
         //call they must not be present in another call. We do that by first creating 
         //an object of the user class which will be the user who initiated the call
         //we find that user from database by using the userrepository and findById method

         User initiator = userRepository.findById(request.getInitiatorId())
         .orElseThrow(()->new IllegalStateException("Initiator not found with id "+request.getInitiatorId()));

         //now we need to check if the status of the user is active or not in a call

         //the existsByUserAndCallSession method checks the database for the user given and then
         //checks if the status field of that user is active or not
         if(callParticipantRepository.existsByUserAndCallSession_CallStatus(initiator,"ACTIVE")) 
         {
              throw new IllegalStateException("User is already active in a call");  
         }

         //after the checks we can be sure that the user exists and is not in a call so he can be 
         //allowed to create a call

         //Now we will create a call session for the user

         CallSession callSession = new CallSession();

         //now we will set the fields in the CallSession entity class

         //setting the user who started the call
         callSession.setInitiator(initiator);

         //setting the uuid of the user
         

         //setting the time when the call is started
         callSession.setStartTime(Instant.now());

         //setting the status of the call
         callSession.setCallStatus("INITIATED");

         //setting the sfuRoomId of the session

        
         callSession.setSfuRoomId("ROOM-" + UUID.randomUUID().toString().substring(0, 8));


         //save the call session type whether it is a video call or voice call
         System.out.println("printing the call type from the request "+request.getCallType());
         callSession.setCallType(request.getCallType());

         //now we will use the CallSession repository to save this data in the database
         callSessionRepository.save(callSession);


         //now we have to setup the CallParticipants entity field

         //now for this we have the List of participants id so we will go through the loop and 
         //for every user entity that we find in the list by using their id we will put them 
         //in the callparticipant

         for (Long participantId : request.getParticipantIds()) 
         {
             // Don't send a notification to the person who is making the call.
             if (participantId.equals(initiator.getId())) 
             {
                 continue;
             }

             // This will send a JSON message like: {"type": "incoming_call", "sfuRoomId": "ROOM-...", "callerName": "Aritra"}
             // to the WebSocket session of the invited user.
             System.out.println("sfuRoomId = " + callSession.getSfuRoomId());
System.out.println("callerName = " + initiator.getFullname());
System.out.println("callType = " + callSession.getCallType());
             videoCallWebSocketHandler.sendMessageToUser(
                     participantId,
                     Map.of(
                         "type", "incoming_call",
                         "sfuRoomId", callSession.getSfuRoomId(),
                         "callerName", initiator.getFullname(),
                         "callType", callSession.getCallType() 
                         )
                         );
             }
         //now we will return the CallResponse data transfer object

         return new CallResponse(
                 callSession.getId(),
                 callSession.getSfuRoomId(),
                 null, // We are not using encryptionKeyId in this simplified version
                 callSession.getStartTime(),
                 callSession.getCallStatus()
                 )   ;

     }


     public void endCall(UUID callId)
     {
         CallSession callSession = callSessionRepository.findById(callId)
                .orElseThrow(() -> new IllegalStateException("Call session not found with ID: " + callId));

         // Update the status and end time.
         callSession.setCallStatus("ENDED");
         callSession.setEndTime(Instant.now());

         // Save the changes to the database.
         // Spring Data JPA knows to perform an UPDATE because the entity already has an ID.
        callSessionRepository.save(callSession);
     }



     //method to find all the users who can join the call
     public List<UserInfo> findUsersByGroup(String groupName)
     {
          return userRepository.findAll().stream()
                .filter(user -> user.getGroupsalloted() != null && user.getGroupsalloted().contains(groupName))
                .map(UserInfo::new) // Convert each User to a UserDTO
                .collect(Collectors.toList());
     }

}

/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "call_sessions")
public class CallSession 
{
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)//this line tells the database to generate the value
     @Column(name="id",nullable=false)
     private UUID id;

     
     @ManyToOne
     @JoinColumn(name = "initiator_user_id", nullable = false) 
     private User initiator;

     @Column(name="sfu_room_id",nullable=false)
     private String sfuRoomId;

     @Column(nullable=false)
     private Instant startTime;

     @Column(name="ended_at")
     private Instant endTime;

     @Column(nullable=false)
     private String callStatus;

     @Column(name = "call_type", nullable = false)
     private String callType;
     //this is a entity class so this will be used to store the data in the database

     //setter for setting the data
     public void setId(UUID id)
     {
         this.id=id;
     }

     //getter for the uuid
     public UUID getId()
     {
         return this.id;
     }

     //setter for setting the initiator id
     public void setInitiator(User initiator)
     {
         this.initiator=initiator;
     }

     //getter for the initiator id
     public User getInitiator()
     {
         return this.initiator;
     }


     public void setSfuRoomId(String sfuRoomId)
     {
         this.sfuRoomId=sfuRoomId;
     }

     //getter for the room id
     public String getSfuRoomId()
     {
         return this.sfuRoomId;
     }

     //setter for the start time
     public void setStartTime(Instant startTime)
     {
         this.startTime=startTime;
     }

     //getter for the startTime
     public Instant getStartTime()
     {
         return this.startTime;
     }

     //setter for the endTime
     public void setEndTime(Instant endTime)
     {
         this.endTime=endTime;
     }

     //getter for the endTime
     public Instant getEndTime()
     {
         return this.endTime;
     }

     //setter for the call status
     public void setCallStatus(String callStatus)
     {
         this.callStatus=callStatus;
     }

     //getter for the call status
     public String getCallStatus()
     {
         return this.callStatus;
     }

     //setter for the callType
     public void setCallType(String callType)
     {
         this.callType=callType;
     }

     //getter for the callType
     public String getCallType()
     {
         return this.callType;
     }
}

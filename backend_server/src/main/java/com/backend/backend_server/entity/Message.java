package com.backend.backend_server.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message 
{

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     // A link to the User who sent the message.
     @ManyToOne
     @JoinColumn(name = "sender_id", nullable = false)
     private User sender;

     // The ID of the group chat this message belongs to (e.g., "command_group").
     @Column(name = "group_id", nullable = false)
     private String groupId;

     // The text content of the message.
     @Column(columnDefinition = "TEXT", nullable = false)
     private String content;

     // The exact time the message was sent.
     @Column(nullable = false)
     private Instant timestamp;

   

     public Message() {}

     public Long getId() 
     {
         return id;
     }

     public void setId(Long id) 
     {
         this.id = id;
     }

     public User getSender() 
     {
         return sender;
     }

     public void setSender(User sender) 
     {
         this.sender = sender;
     }

     public String getGroupId() 
     {
         return groupId;
     }

     public void setGroupId(String groupId) 
     {
         this.groupId = groupId;
     }

     public String getContent() 
     {
         return content;
     }

     public void setContent(String content) 
     {
         this.content = content;
     }

     public Instant getTimestamp() 
     {
         return timestamp;
     }

     public void setTimestamp(Instant timestamp) 
     {
         this.timestamp = timestamp;
     }
}

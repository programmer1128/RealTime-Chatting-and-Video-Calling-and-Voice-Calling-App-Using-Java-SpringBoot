package com.backend.backend_server.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "GroupMessages345")
public class GroupMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonProperty("groupId")
    public Long getGroupId() {
    return this.group != null ? this.group.getId() : null;
}

    private LocalDateTime timestamp;

    public GroupMessage() {}

    public GroupMessage(Group group, User sender, String content, LocalDateTime timestamp) {
        this.group = group;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

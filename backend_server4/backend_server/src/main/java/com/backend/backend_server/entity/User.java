package com.backend.backend_server.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Users345", uniqueConstraints = {@UniqueConstraint(columnNames = {"name","mobileNumber"})})
public class User 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mobileNumber;

     @ManyToMany(fetch = FetchType.LAZY)
     @JoinTable(
         name = "user_groups", //  name of  relationship table
         joinColumns = @JoinColumn(name = "user_id"),
         inverseJoinColumns = @JoinColumn(name = "group_id")
         )
     private Set<GroupEntity> groups = new HashSet<>();

    // Constructors
    public User() {}
    public User(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }


    //setter for the set of group
     public void setGroups(Set<GroupEntity> groups) 
     { 
         this.groups = groups; 
     }
      
     //getter for the set of groups

     public Set<GroupEntity> getGroups() 
     { 
         return groups; 
     }

      public void addGroup(GroupEntity group) 
      {
         this.groups.add(group);
         group.getUsers().add(this);
     }
}

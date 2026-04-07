package com.backend.backend_server.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "satadru_groups") //storing names
public class GroupEntity 
{

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(unique = true, nullable = false)
     private String groupName;

     // "mappedBy" points to the 'groups' field in the User entity
     @ManyToMany(mappedBy = "groups")
     private Set<User> users = new HashSet<>();

  
     public Long getId() 
     { 
         return id; 
     }
     public void setId(Long id) 
     { 
         this.id = id; 
     }
     public String getGroupName() 
     { 
         return groupName; 
     }
     public void setGroupName(String groupName) 
     { 
         this.groupName = groupName; 
     }
     public Set<User> getUsers() 
     { 
         return users; 
     }
     public void setUsers(Set<User> users) 
     { 
         this.users = users; 
     }

     public void addUser(User user) 
     {
         this.users.add(user);
         user.getGroups().add(this);
     }
}
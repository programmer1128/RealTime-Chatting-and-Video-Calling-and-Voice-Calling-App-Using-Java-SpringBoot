package com.backend.backend_server.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="users2")
public class User 
{
     @Id //marks this as the primary key
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     private long id;

     @Column(nullable=false)
     private String username;

     @Column(name="name",nullable=false)
     private String fullname;

     @Column(unique=true,nullable=false)
     private String personelid;


     @Column(unique=true,nullable=false)
     private String email;

     @Column(nullable=false)
     private String role;

     @ElementCollection
     private List<String> groupsalloted;

     @Column(nullable=true)
     private String password;

     @Column(nullable=true)
     private String otp;
     //default constructor


     @Column(nullable=true)
     private LocalDateTime otpExpiry;

     public User()
     {

     }

     //now we add the getters and setters for all the fields that our user will have

     //setter for the id
     public void setId(long id)
     {
         this.id=id;
     }
     //getter for the id
     public long getId()
     {
         return this.id;
     }


     //setter for the username
     public void setUsername(String username)
     {
         this.username=username;
     }
     
     //getter for the username
     public String getUsername()
     {
         return this.username;
     }

     //setter for the fullname
     public void setFullname(String fullname)
     {
         this.fullname=fullname;
     }

     //getter for the fullname
     public String getFullname()
     {
         return this.fullname;
     }

     //setter for the personelid
     public void setPersonelid(String personelid)
     {
         this.personelid=personelid;
     }

     //getter for the personel id
     public String getPersonelid()
     {
         return this.personelid;
     }

     //setter for the email
     public void setEmail(String email)
     {
         this.email=email;
     }

     //getter for the email
     public String getEmail()
     {
         return this.email;
     }

     //setter for the role
     public void setRole(String role)
     {
         this.role=role;
     }
      
     //getter for the role
     public String getRole()
     {
         return this.role;
     }

     //now for the groups alloted
     public void setGroupsalloted(List<String> groupsalloted)
     {
         this.groupsalloted=groupsalloted;
     }

     //getter for the groups alloted
     public List<String> getGroupsalloted()
     {
         return this.groupsalloted;
     }

     //setter for the otp
     public void setOtp(String otp)
     {
         this.otp=otp;
     }

     //getter for the otp
     public String getOtp()
     {
         return this.otp;
     }

     //setter for the password
     public void setPassword(String password)
     {
         this.password=password;
     }
      
     //getter for the password
     public String getPassword()
     {
         return this.password;
     }

     //setter for the Otpexpury time
     public void setOtpExpiry(LocalDateTime otpExpiry)
     {
         this.otpExpiry= otpExpiry;
     }

     //getter for the otpexpiry

     public LocalDateTime getOtpExpiry()
     {
         return this.otpExpiry;
     }
     
}


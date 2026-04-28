/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;

//This is a data transfer object that will serve as the json for sending back a response for 
//successful login of the user.
import java.util.List;

public class LoginResponse 
{
     private String username;
     private String fullname;
     private String personelid;
     private String email;
     private String role;
     private List<String> groupsAlloted;
     private Long id;

     //setter for the id of the user
     public void setId(Long id)
     {
         this.id=id;
     }

     //getter for the id
     public Long getId()
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

     //setter for personelid
     public void setPersonelid(String personelid)
     {
         this.personelid=personelid;
     }

     //getter for the personelid
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

     //setter for the groups alloted to the user
     public void setGroupsAlloted(List<String> groupsAlloted)
     {
         this.groupsAlloted=groupsAlloted;
     }

     //getter for the groups alloted to the user

     public List<String> getGroupsAlloted()
     {
         return this.groupsAlloted;
     }

}

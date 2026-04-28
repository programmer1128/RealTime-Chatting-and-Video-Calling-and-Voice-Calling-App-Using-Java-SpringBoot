/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;

//This is a data transfer object class that will hold the data of the json sent by the frontend when the 
//Super Admin is creating a new user. This json will contain the following fields 

//Full name
//Personel ID
//Email
//ROLE
//Group alloted
import java.util.List;

public class CreateUserRequest 
{
     private String fullname;
     private String username;
     private String personelid;
     private String email;

     //we are using enum here in roles instead of strings because in creating the user there can be 
     //hq_admin or hq_admi a typo which the enum will strictly avoid
     private String role;
     //the groups alloted is taken as an arraylist of strings as the new user may be part of multiple
     //groups like a high ranking officer may be part of hq group, family group, forces group. Hence 
     //we use the arraylist
     private List<String> groupsalloted;


     //now for every field we will setup the getters and setters. The jackson will use the setters to 
     //store the data from the json inside these fields while the getters will be used by us to retrieve
     //that data using objects of this dto class

     //setter for the fullname
     public void setFullname(String fullname)
     {
         this.fullname=fullname;
     }

     //getter for the full name
     public String getFullname()
     {
         return this.fullname;
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
     
     //setter for the personelid
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

     //setter for the groupsalloted
     public void setGroupsalloted(List<String> groupsalloted)
     {
         this.groupsalloted=groupsalloted;
     }

     //getter for the groupsalloted
     public List<String> getGroupsalloted()
     {
         return this.groupsalloted;
     }
}

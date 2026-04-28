/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;

//This is a data transfer for the login of the user. It has 2 fields username and the password that 
//is entered by the user and recieved as a json from the frontend and converted to a java object using
//this class

public class LoginRequest 
{
     private String username;
     private String password;

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
}

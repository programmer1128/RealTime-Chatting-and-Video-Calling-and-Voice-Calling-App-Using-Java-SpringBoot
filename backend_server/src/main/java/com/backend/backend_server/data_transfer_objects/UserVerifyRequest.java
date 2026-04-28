/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;

//import javax.validation.constraints.NotBlank;

//The DTO data transfer objects is a class that is required to convert the raw json data format to a 
//java object. what happens is that the jackson library of the spring framework helps us to convert 
//this raw data to a java object to use in our program. When a request is sent from the frontend in a
//json the tomcat server recieves it. It tells the jackson to decipher the json. The jackson tool scans
//the json and then accordingly scans the dto while setting the respective data to the respective variables
//using the setters methods and then we can access the data using the getters methods

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class UserVerifyRequest 
{

     //@NotBlank(message = "Username cannot be blank")
     @NotBlank(message="Username cannot be blank")
     private String username;

     @NotBlank(message="Password cannot be blank")
     private String password;

     @NotBlank(message="OTP cannot be blank")
     @Size(min=6,max=6,message="OTP must be 6 digits")
     private String otp;
     
     //setters to set the data of the json to java variables. for the name of the methods we have to follow
     //the java beans naming convention which states the name of the setter is set+ first letter capital of
     //the name of the field 
     public void setUsername(String username)
     {
         this.username=username;
     }

     //now we define the getters. this is for programmers to access the data of the json using objects
     //for our own logic

     //method to retrieve username
     public String getUsername()
     {
         return this.username;
     }

     public void setPassword(String password)
     {
         this.password=password;
     }

     //method to retrieve password
     public String getPassword()
     {
         return this.password;
     }


     //setter for the opt
     public void setOtp(String otp)
     {
         this.otp=otp;
     }

     //method to retreive the otp
     public String getOtp()
     {
         return this.otp;
     }

     
}

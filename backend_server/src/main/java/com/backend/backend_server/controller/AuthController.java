package com.backend.backend_server.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.data_transfer_objects.LoginRequest;
import com.backend.backend_server.data_transfer_objects.LoginResponse;
import com.backend.backend_server.data_transfer_objects.UserVerifyRequest;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.service.AppLoginService;
import com.backend.backend_server.service.AuthService;
@RestController 
@RequestMapping("/auth")
public class AuthController 
{ 

     @Autowired
     AuthService authService;

     @Autowired
     AppLoginService appLoginService;

     @PostMapping("/user/verify")
     public ResponseEntity<?> verifyUser(@RequestBody UserVerifyRequest request)
     {
         try
         {
             User user=authService.validateLogin(request);
             LoginResponse response = new LoginResponse();

             //making the response that will be send as json to the frontend
             response.setEmail(user.getEmail());
             response.setUsername(user.getUsername());
             response.setFullname(user.getFullname());
             response.setGroupsAlloted(user.getGroupsalloted());
             response.setId(user.getId());
             response.setPersonelid(user.getPersonelid());
             response.setRole(user.getRole());

             return ResponseEntity.ok(response);   
         }
         catch(RuntimeException e)
         {  
             Map<String,String> error= Map.of("message","invalid usernameorpassword");
             return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
         }
         

         //return ResponseEntity.ok(response);   
     }

     @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody LoginRequest request)
     {
         User user = appLoginService.validatePassword(request);
         if(user!=null)
         {
             //the password entered by the user is correct and we will response dto and send it
             //back to the frontend
             LoginResponse response = new LoginResponse();

             //making the response that will be send as json to the frontend
             response.setEmail(user.getEmail());
             response.setUsername(user.getUsername());
             response.setFullname(user.getFullname());
             response.setGroupsAlloted(user.getGroupsalloted());
             response.setId(user.getId());
             response.setPersonelid(user.getPersonelid());
             response.setRole(user.getRole());

             return ResponseEntity.ok(response);
         }
         Map<String,String> error= Map.of("message","invalid usernameorpassword");
         return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
         //return appLoginService.validatePassword(request); 
     }

}

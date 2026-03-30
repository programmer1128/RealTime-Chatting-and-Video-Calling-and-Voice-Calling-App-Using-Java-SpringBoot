package com.backend.backend_server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backend_server.data_transfer_objects.CreateUserRequest;
import com.backend.backend_server.service.CreateUserService; 
 
@RestController
@RequestMapping("/admin/create-user") 
public class CreateUserController  
{
     @Autowired
     CreateUserService userService;

     @PostMapping("/create")
     public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request)
     {
         //now that we have the json converted to a java object by using jackson and the CreateUserRequest
         //DTO we can now use this object in our code
         //we will send the dto object to the service class for registration of user

         //now here we are implementing a try catch block because the user may not be created
         //due to some reasons like the user already existing.In that case, we will have to 
         //catch the exception and reply accordingly.
         try
         {
             userService.registerNewUser(request);
             Map<String,String> successResponse=Map.of("message","user registered successfully");

             return new ResponseEntity<>(successResponse,HttpStatus.CREATED);

         }
         catch(IllegalStateException e)
         {
             Map<String,String> errorResponse= Map.of("error",e.getMessage());
             return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);   
         }
         

         //return ResponseEntity.ok().body("success");
     }
     
}

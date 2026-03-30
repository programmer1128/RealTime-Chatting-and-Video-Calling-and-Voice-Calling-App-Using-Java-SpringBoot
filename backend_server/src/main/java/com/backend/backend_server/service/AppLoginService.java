package com.backend.backend_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend_server.data_transfer_objects.LoginRequest;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;

@Service
public class AppLoginService 
{
     @Autowired
     UserRepository userRepository;

     @Autowired
     PasswordEncoder passwordEncoder;
     //this class takes the username and the password of the user as recieved in the json and 
     //checks if the password entered by the user is correct of not.
     public User validatePassword(LoginRequest request)
     {
         //now we first have to find the user with the username in the request
         User user = userRepository.findByUsername(request.getUsername())
                 .orElse(null);
         if(user==null)
         {
             return null;
         }
         //now while creating the user only we have found that whether it exists or not. now what 
         //we need to do is check for the password.Now the password is hashed so in the database
         //too the password is stored in a hashed form

         //so we use spring's passWordEncoder.matches() method to compare the password user has used 
         //and the password stored in the database
         if(passwordEncoder.matches(request.getPassword(), user.getPassword()))
         {
             return user;
         }
         return null;
     }    
}

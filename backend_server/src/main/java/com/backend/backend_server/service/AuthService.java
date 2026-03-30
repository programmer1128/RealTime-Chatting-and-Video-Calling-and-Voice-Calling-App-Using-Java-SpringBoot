package com.backend.backend_server.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backend_server.data_transfer_objects.UserVerifyRequest;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;

@Service
public class AuthService 
{
     //here we define the logic for the valid login of the user
     @Autowired
     private UserRepository userRepository;


     @Autowired
     private PasswordEncoder passwordEncoder;


     public User validateLogin(UserVerifyRequest request)
     {
         //the very first thing we need to check is if the otp is valid or not
         //for that we will need the OTP that is sent to thbis user only. How do we
         //get that? We use the UserRepository class to get us the user with the 
         //desired username. There from the database we check the otp

         //getting the user for whom we are performing the validation check
         User loginUser = userRepository.findByUsername(request.getUsername())
         .orElseThrow(() -> new RuntimeException("Error: User not found."));

         //now for this user we check if the otp that is entered by the user is same 
         //as the otp that was sent to them
         System.out.println("otp is "+request.getOtp());
         System.out.println("otp should be "+loginUser.getOtp());
         if(request.getOtp()==null||!(request.getOtp().equals(loginUser.getOtp())))
         {
             System.out.println("hello");
             System.out.println(request.getOtp());
             throw new RuntimeException("Error otp is incorrect");
             
         }
         else if(LocalDateTime.now().isAfter(loginUser.getOtpExpiry()))
         {
             throw new RuntimeException("Error otp is expired");
         }

          //now that we are sure that the otp is valid we can continue to save the password of
          //the user in the database and set the otp and otp expiry fields to null

          //save the password
          loginUser.setPassword(passwordEncoder.encode(request.getPassword()));

          //now we set the otp and otp expiry to null

          loginUser.setOtp(null);
          loginUser.setOtpExpiry(null);

          //we save the configurations of the user
          userRepository.save(loginUser);

          return loginUser;
     }
}

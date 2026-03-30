package com.backend.backend_server.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.backend_server.data_transfer_objects.CreateUserRequest;
import com.backend.backend_server.entity.User;
import com.backend.backend_server.repository.UserRepository;


@Service
public class CreateUserService 
{
     //now in this class we define the methods to register a new user
      @Autowired    
      private UserRepository userRepository;


       @Autowired
      private EmailService emailService;
      public void registerNewUser(CreateUserRequest request)
      {
           //now here we define the logic to save the user in the 
           //database using the user entity.Now we can also check
           //if the user exists in the database by using the userrepository
           // 2 users cannot have same username or the same mail or personel
           //id 

           if (userRepository.existsByEmail(request.getEmail())) 
           { 
                System.out.println("user already exists");
                throw new IllegalStateException("Error user with same email exists");
           }
           else if(userRepository.existsByPersonelid(request.getPersonelid()))
           {
                System.out.println("user exists with same personel id");
                throw new IllegalStateException("Error user with same personel id exists");
           }

           //now if the user does not exist then we can confirm that we can register the new user

           //for registering the new user to the database we will use the User Entity class 
           
           //first we create a new user object
           
           User new_user = new User();

           //registering the new user using the user entity
           new_user.setEmail(request.getEmail());
           new_user.setFullname(request.getFullname());
           new_user.setGroupsalloted(request.getGroupsalloted());
           new_user.setRole(request.getRole());
           new_user.setPersonelid(request.getPersonelid());
           new_user.setUsername(request.getUsername());

           //now we will use the random function  to create a random 6 digit otp

           //saving the new user in the database using the user repository
           Random number= new Random();
           
           int otp=number.nextInt(900000)+100000;

           new_user.setOtp(String.valueOf(otp));

           //now that we have saved the otp we will use the EmailService to send the otp to
           //the concerned user 
           

           emailService.sendEmail(request.getEmail(),String.valueOf(otp));
            
           //now after email is sent we set the time
           new_user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

           userRepository.save(new_user);

           
      }
}

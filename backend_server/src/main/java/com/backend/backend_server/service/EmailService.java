package com.backend.backend_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService 
{
     //this class will be used to send email. Now for that we will need the email address of the 
     //user which we will get by from the create user service class
     @Autowired
     JavaMailSender mailSender;


     public void sendEmail(String emailAddress,String otp)
     {
         SimpleMailMessage message = new SimpleMailMessage();

         message.setTo(emailAddress);

         message.setSubject("Your account Activation OTP - ");

         message.setText("Your One-Time Password to activate your account is: " + otp
                + "\n\nThis code will expire in 10 minutes.");

         mailSender.send(message);

         System.out.println("OTP email sent to: " + emailAddress);
     }
}

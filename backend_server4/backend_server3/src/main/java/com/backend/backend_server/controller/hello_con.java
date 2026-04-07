package com.backend.backend_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class hello_con 
{
     @GetMapping("/hello")
     public String hello()
     {
         return "hello";
     }
}

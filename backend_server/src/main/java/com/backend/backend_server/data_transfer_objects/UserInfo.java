/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;

import com.backend.backend_server.entity.User;

public class UserInfo 
{
     private Long id;
     private String fullname;
     private String username;

     //constructor to initialise the fields with their values

     public UserInfo(User user)
     {
         this.id=user.getId();
         this.fullname=user.getFullname();
         this.username=user.getUsername();
     }

     //now we define the getters for all the fields

     public Long getId()
     {
         return this.id;
     }

     public String getFullname()
     {
         return this.fullname;
     }

     public String getUsername()
     {
         return this.username;
     }
}

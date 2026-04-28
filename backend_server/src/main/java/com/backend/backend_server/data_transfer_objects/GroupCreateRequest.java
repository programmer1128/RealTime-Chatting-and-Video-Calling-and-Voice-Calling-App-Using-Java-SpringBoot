/*
 * Copyright (c) 2026 Aritra Banerjee. All Rights Reserved.
 * GitHub: https://github.com/programmer1128
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.backend.backend_server.data_transfer_objects;

import java.util.ArrayList;
import java.util.List;

//This is a data transfer object class that will convert the json coming for '
//creaction of groups
public class GroupCreateRequest
{
     private Long creatorId;
     private String groupName;
      private List<String> userNames = new ArrayList<>();;
     
     
     //setter for the creator id
     public void setCreatorId(Long creatorId)
     {
         this.creatorId=creatorId;
     }  

     //getter for the creatorid
     public Long getCreatorId()
     {
         return this.creatorId;
     }

     //setter for  the groupname

     public void setGroupName(String groupName)
     {
         this.groupName=groupName;
     }

     //getter for the grouopname
     public String getGroupName()
     {
         return this.groupName;
     }

     //setter for the list of usernames
     public void setUserNames(List<String> userNames)
     {
         this.userNames=userNames;
     }

     //getter for the usenrnames
     public List<String> getUserNames()
     {
         return this.userNames;
     }
}

package com.backend.backend_server.controller;

import com.backend.backend_server.dto.GroupDTO;
import com.backend.backend_server.entity.Group;
import com.backend.backend_server.service.AdminGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/admin/groups")
public class AdminGroupController {

    @Autowired
    private AdminGroupService adminGroupService;

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(adminGroupService.getAllGroups());
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupDTO groupDto) {
        return ResponseEntity.ok(adminGroupService.createGroup(groupDto.groupName(), new ArrayList<>(groupDto.memberIds())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        adminGroupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/members")
    public ResponseEntity<Group> updateMembers(@PathVariable Long id, @RequestBody List<Long> userIds) {
        return ResponseEntity.ok(adminGroupService.updateGroupMembers(id, userIds));
    }
}

package com.backend.backend_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend_server.entity.GroupEntity;

public interface GroupEntityRepository extends JpaRepository<GroupEntity, Long> 
{
    
}

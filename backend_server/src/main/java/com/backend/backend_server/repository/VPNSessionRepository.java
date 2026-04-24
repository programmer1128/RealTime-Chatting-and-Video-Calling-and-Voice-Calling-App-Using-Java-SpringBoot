package com.backend.backend_server.repository;

import com.backend.backend_server.entity.VPNSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VPNSessionRepository extends JpaRepository<VPNSession, Long> {
    // When porting to redis, delete findAll, findByID, save
    List<VPNSession> findAll();
    Optional<VPNSession> findById(Long id);
    VPNSession save(VPNSession session);
}

package com.backend.backend_server.repository;

import com.backend.backend_server.entity.Group;
import com.backend.backend_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMembersContaining(User user);
}

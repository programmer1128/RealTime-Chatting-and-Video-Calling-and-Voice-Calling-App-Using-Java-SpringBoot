package com.backend.backend_server.repository;

import com.backend.backend_server.entity.GroupMessage;
import com.backend.backend_server.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
    List<GroupMessage> findByGroupOrderByTimestampAsc(Group group);
}

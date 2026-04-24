package com.backend.backend_server.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.backend.backend_server.entity.Role;
import com.backend.backend_server.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPersonnelId(String personnelId);
    Optional<User> findByMobileNumber(String mobileNumber);
    List<User> findByRole(Role role);
    boolean existsByEmail(String email);
    boolean existsByPersonnelId(String personnelId);
}

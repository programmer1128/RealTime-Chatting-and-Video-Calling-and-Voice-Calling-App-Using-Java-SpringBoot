package com.backend.backend_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.backend_server.entity.User;

public interface UserRepository extends JpaRepository<User, Long> 
{
    Optional<User> findByNameAndMobileNumber(String name, String mobileNumber);

    Optional<User> findByName(String name);
}

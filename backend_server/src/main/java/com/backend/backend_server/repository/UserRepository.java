package com.backend.backend_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.backend_server.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> 
{
     //query to find a user by their email
     Optional<User> findByEmail(String email);

     //custom query to check if a  user with the given email exists

     boolean existsByEmail(String email);

     Optional<User> findByPersonelid(String personelid);

     boolean existsByPersonelid(String personelid);
     
     Optional <User> findByUsername(String username);

     boolean existsByUsername(String username);


     Optional<User> findById(long id);

     boolean existsById(long id);

}

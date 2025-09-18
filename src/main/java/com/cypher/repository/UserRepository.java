package com.cypher.repository;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cypher.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findByUsername(@NotEmpty String username);
    Optional<User> findByEmail(@NotEmpty String email);
}

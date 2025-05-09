package com.hemonexus.repository;

import com.hemonexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    /* role helpers */
    List<User> findByRoles_Name(String roleName);
}

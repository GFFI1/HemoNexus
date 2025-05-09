package com.hemonexus.repository;

import com.hemonexus.model.ERole;
import com.hemonexus.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name); // Return a list to handle multiple results
}
package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}

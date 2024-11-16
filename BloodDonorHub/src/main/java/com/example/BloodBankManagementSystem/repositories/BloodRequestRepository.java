package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.BloodRequest;
import com.example.BloodBankManagementSystem.entities.PriorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    @Query(value = "SELECT r FROM BloodRequest r where r.username=:username")
    List<BloodRequest> findByUsername(@Param("username") String username);

    @Query(value = "SELECT r FROM BloodRequest r where r.bankId=:bankId")
    List<BloodRequest> findByBankId(@Param("bankId") Integer bankId);

    List<BloodRequest> findByPriorityType(PriorityType priorityType);
}

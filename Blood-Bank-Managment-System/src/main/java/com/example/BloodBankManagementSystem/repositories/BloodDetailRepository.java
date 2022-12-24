package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.BloodDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDetailRepository extends JpaRepository<BloodDetail,Integer> {
}

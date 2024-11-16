package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodBankRepository extends JpaRepository<BloodBank,Integer> {

    List<BloodBank> findByCity(String city);

    //this will help us to link the manager with it's corresponding bankId
    BloodBank findByUsername(String username);
}

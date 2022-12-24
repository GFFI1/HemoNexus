package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.Compaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompaignRepository extends JpaRepository<Compaign,Integer> {
    //manager would be able to see all the compaign belonging to his bank
    public List<Compaign> findByBankId(Integer bankId);

    //this will help user to donate the blood in a better way
    public List<Compaign> findByCity(String city);
}

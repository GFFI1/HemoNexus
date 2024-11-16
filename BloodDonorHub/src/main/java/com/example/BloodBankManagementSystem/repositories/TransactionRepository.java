package com.example.BloodBankManagementSystem.repositories;

import com.example.BloodBankManagementSystem.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    public List<Transaction> findByUsername(String username);
    public List<Transaction> findByBankId(Integer bankId);
}

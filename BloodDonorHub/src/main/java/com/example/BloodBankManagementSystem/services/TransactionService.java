package com.example.BloodBankManagementSystem.services;

import com.example.BloodBankManagementSystem.entities.Transaction;
import com.example.BloodBankManagementSystem.payload.TransactionDto;

import java.util.List;

public interface TransactionService {

    public List<TransactionDto> getAllTransactions();
    public TransactionDto getTransactionById(Integer transactionId);
    List<TransactionDto> getTransactionByUsername(String username);
    List<TransactionDto> getAllTransactionBank();
    List<TransactionDto> getAllTransactionOfLoggerInUser();

}

package com.example.BloodBankManagementSystem.services.impl;

import com.example.BloodBankManagementSystem.entities.BloodBank;
import com.example.BloodBankManagementSystem.entities.Transaction;
import com.example.BloodBankManagementSystem.exceptions.NotFoundException;
import com.example.BloodBankManagementSystem.payload.TransactionDto;
import com.example.BloodBankManagementSystem.repositories.BloodBankRepository;
import com.example.BloodBankManagementSystem.repositories.TransactionRepository;
import com.example.BloodBankManagementSystem.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions=transactionRepository.findAll();
        return DtoListFromEntityList(transactions);
    }

    @Override
    public TransactionDto getTransactionById(Integer transactionId) {
        Transaction transaction=getTransaction(transactionId);
        return EntityToDto(transaction);
    }

    @Override
    public List<TransactionDto> getTransactionByUsername(String username) {
        List<Transaction> transactions=transactionRepository.findByUsername(username);
        return DtoListFromEntityList(transactions);
    }

    @Override
    public List<TransactionDto> getAllTransactionBank() {
        String managerUsername=getUserNameFromToken();
        //now fetch the bank corresponding to this manager..
        BloodBank bloodBank=bloodBankRepository.findByUsername(managerUsername);
        //now fetch all the transactions that happened with that bank....
        List<Transaction> transactions=transactionRepository.findByBankId(bloodBank.getBankId());
        return DtoListFromEntityList(transactions);

    }

    @Override
    public List<TransactionDto> getAllTransactionOfLoggerInUser() {
         String username=getUserNameFromToken();
         List<Transaction> transactions=transactionRepository.findByUsername(username);
         return DtoListFromEntityList(transactions);
    }

    public List<TransactionDto> DtoListFromEntityList(List<Transaction> transactions){
        List<TransactionDto> transactionDtos=new ArrayList<>();
        transactions.stream().map((transaction)->{
            return transactionDtos.add(EntityToDto(transaction));
        }).collect(Collectors.toList());
        return transactionDtos;
    }

    public TransactionDto EntityToDto(Transaction transaction){
        return modelMapper.map(transaction,TransactionDto.class);
    }

    public Transaction getTransaction(Integer transactionId){
        if(transactionRepository.findById(transactionId).isPresent()){
            return transactionRepository.findById(transactionId).get();
        }
        throw new NotFoundException("Transaction with the id "+transactionId+" is not in the database");
    }
    public String getUserNameFromToken(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }
}

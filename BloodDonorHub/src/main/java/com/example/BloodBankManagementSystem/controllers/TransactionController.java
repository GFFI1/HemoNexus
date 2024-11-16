package com.example.BloodBankManagementSystem.controllers;

import com.example.BloodBankManagementSystem.payload.TransactionDto;
import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import com.example.BloodBankManagementSystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Response<List<TransactionDto>> getAllTransactions(){
        List<TransactionDto> transactionDtos=transactionService.getAllTransactions();
        Response<List<TransactionDto>> response=new Response<>();
        response.setBody(transactionDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched all the transactions"));
        return response;
    }

    @GetMapping(value="/{transactionId}")
    public Response<TransactionDto> getTransactionById(@PathVariable("transactionId") Integer transactionId){
        TransactionDto transactionDto=transactionService.getTransactionById(transactionId);
        Response<TransactionDto> response=new Response<>();
        response.setBody(transactionDto);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the transaction By Id"));
        return response;
    }

    @GetMapping(value="/username/{username}")
    public Response<List<TransactionDto>> getTransactionByUsername(@PathVariable("username") String username){
        List<TransactionDto> transactionDtos=transactionService.getTransactionByUsername(username);
        Response<List<TransactionDto>> response=new Response<>();
        response.setBody(transactionDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the transaction By Username"));
        return response;
    }

    @GetMapping(value = "/loggeduser")
    public Response<List<TransactionDto>> getTransactionOfLoggedInUser(){
        List<TransactionDto> transactionDtos=transactionService.getAllTransactionOfLoggerInUser();
        Response<List<TransactionDto>> response=new Response<>();
        response.setBody(transactionDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched the transaction of the currently logged in user"));
        return response;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/bank")
    public Response<List<TransactionDto>> getTransactionOfBank(){
        List<TransactionDto> transactionDtos=transactionService.getAllTransactionBank();
        Response<List<TransactionDto>> response=new Response<>();
        response.setBody(transactionDtos);
        response.setStatus(new Status(HttpStatus.OK.value(), true,"Fetched All the transactions of the bank whose manager is the logged in user"));
        return response;
    }




}

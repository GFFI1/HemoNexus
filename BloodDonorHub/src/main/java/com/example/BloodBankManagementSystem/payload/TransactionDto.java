package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class TransactionDto {


    private int transactionId;
    private BloodType bloodType;
    private int quantity;
    private int bankId;
    private int ticketId;
    private Date createdOn;
    private String username;
}

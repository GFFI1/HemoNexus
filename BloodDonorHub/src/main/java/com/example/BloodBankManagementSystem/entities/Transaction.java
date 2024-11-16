package com.example.BloodBankManagementSystem.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    private BloodType bloodType;

    private int quantity;

    private int bankId;

    //corresponding to a ticket transaction will be generated...
    private int ticketId;

    private Date createdOn;

    //the person who is getting the blood or donating the bloood.
    private String username;
}

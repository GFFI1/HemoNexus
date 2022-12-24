package com.example.BloodBankManagementSystem.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/*->Ticket will be generated in 2 cases
1->when the manager approves the appointment in case when user wants to donate
2->when the manager approves the blood-request in case when the user wants to request the blood */
@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    private BloodType bloodType;

    private int quantity;

    private String username;

    private int bankId;

    private Date createdOn;

    private Date validDate;

    //initially it will be false when the user will visit to hospital then manager will set it to true
    private Boolean redeem;

    private Boolean donor;
}

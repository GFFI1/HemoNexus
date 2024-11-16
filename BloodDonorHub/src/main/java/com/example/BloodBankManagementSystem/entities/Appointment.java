package com.example.BloodBankManagementSystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//this is when the user want's to donate the blood either through compaign or through bank
@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private int appointmentId;

    //this we will not ask from the user we will fetch it from the db bcz we have the user-details
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(nullable = false)
    private int quantity;

    //of the user who has created this appointment
    private String username;

    @Column(name="bank_id")
    private int bankId;

    @Column(name="created_on")
    private Date createdOn;

    //initially it will be set to false
    @Column(name="approve")
    private Boolean approve;
}

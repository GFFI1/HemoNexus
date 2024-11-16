package com.example.BloodBankManagementSystem.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BloodDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bloodDetailId;

    private BloodType bloodType;

    private Long amount; //initially it will be set to zero...

    @Column(name="blood_bank_id")
    private int Blood_Bank_Id;
}

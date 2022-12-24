package com.example.BloodBankManagementSystem.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blood_bank_id")
    private int BankId;

    @Column(name="blood_bank_name",nullable = false,unique = true)
    private String BankName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    private String pincode;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(name="mobile_number")
    private String mobileNumber;

    //we will only save the username we won't save the rest details about the manager
    @Column(name="manager_username")
    private String username;


    //will make a hash-map and key will be the type of blood and value be the quantity.
    //HashMap<BloodType,Integer> bloodQuantity=new HashMap<>();
    //on doing re-run the amount of blood will be set to 0 again...

    //we need to initialise this list by all the types of blood and set initial amount as zero.
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="blood_bank_id",referencedColumnName = "blood_bank_id")
    List<BloodDetail> bloodDetailList=new ArrayList<>();
}

package com.example.BloodBankManagementSystem.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Compaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="compaign_id")
    private int compaignId;

    //this will be filled in the backend only...
    private String city;
    private String state;
    private int bankId;
    private Date createdOn;

    //this will come from the DTO...
    @Column(nullable = false)
    private String locality;

    @Column(nullable = false)
    private Date start_Date;

    @Column(nullable = false)
    private Date end_Date;

}

package com.example.BloodBankManagementSystem.payload;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//this is the response that we will send back to the client-side after creating the compaign
//same as that of the entity so that we can do the direct mapping b/w the Dto and entity
@Data
public class CompaignResponseDto {
    private int compaignId;
    private String city;
    private String state;
    private int BankId;
    private Date createdOn;
    private String locality;
    private Date start_Date;
    private Date end_Date;
}

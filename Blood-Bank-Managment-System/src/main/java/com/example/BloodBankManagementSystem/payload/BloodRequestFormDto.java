package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import lombok.Data;

//this will come from the client-side when the user will choose the request option
@Data
public class BloodRequestFormDto {

    private BloodType bloodType;
    private String city;
}

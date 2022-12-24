package com.example.BloodBankManagementSystem.payload;

import lombok.Data;

//this is basically the DTO needed at the time of login....
@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}

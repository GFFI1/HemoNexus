package com.example.BloodBankManagementSystem.payload;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String jwtToken;
    private UserDto userDto;
}

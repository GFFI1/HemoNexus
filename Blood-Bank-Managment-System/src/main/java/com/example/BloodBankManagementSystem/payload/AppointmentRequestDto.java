package com.example.BloodBankManagementSystem.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//this will have the details that the user will fill at the time of generating the appointment
@Data
public class AppointmentRequestDto {

    @NotNull
    @NotEmpty(message = "Choose the bank where u want to donate the blood")
    private int bankId;

    @NotNull
    @NotEmpty(message = "How much u want to donate")
    private int quantity;
}

package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class AppointmentResponseDto {


    private int appointmentId;
    private BloodType bloodType;
    private int quantity;
    private String username;
    private int bankId;
    private Date createdOn;
    private Boolean approve;
}

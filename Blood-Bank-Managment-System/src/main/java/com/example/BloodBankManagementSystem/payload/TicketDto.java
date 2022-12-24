package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import lombok.Data;
import java.util.Date;

@Data
public class TicketDto {

    private int ticketId;
    private BloodType bloodType;
    private int quantity;
    private String username;
    private int bankId;
    private Date createdOn;
    private Date validDate;
    private Boolean redeem;
    private Boolean donor;
}

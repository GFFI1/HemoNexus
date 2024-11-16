package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import com.example.BloodBankManagementSystem.entities.PriorityType;
import com.example.BloodBankManagementSystem.entities.StatusType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BloodRequestResponseDto {

    private Long requestId;
    private BloodType bloodType;
    private Integer quantity;
    private Integer bankId;
    private String username;
    private Date createdOn;
    private StatusType statusType;
    private PriorityType priorityType;
    private String discription;
}

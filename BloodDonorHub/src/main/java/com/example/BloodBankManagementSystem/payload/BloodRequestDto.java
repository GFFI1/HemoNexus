package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import com.example.BloodBankManagementSystem.entities.PriorityType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BloodRequestDto {



    @NotNull
    private BloodType bloodType;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer bankId;

    private PriorityType priorityType;

    @NotNull
    @NotEmpty
    private String discription;
}

package com.example.BloodBankManagementSystem.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data //this is the data that the manger will gonna to send from the client-side
public class CompaignRequestDto {

    @NotNull
    @NotEmpty
    private String locality;

    //josn-format simply shows that the date is coming in this format from the client-side
    @NotNull
    @NotEmpty
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date start_Date;

    @NotNull
    @NotEmpty
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date end_Date;
}

package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodDetail;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class BloodBankDto {

    private int BankId;

    @NotNull
    @NotEmpty
    private String BankName;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String state;

    @NotNull
    @Length(min=6,max = 6,message = "pincode should have 6 characters")
    private String pincode;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min=10,max = 10,message = "Mobile number should have 10 digits")
    private String mobileNumber;


    //details of the manager....
    private String username;
    private String password;
    private String manager_email;
    private String firstName;
    private String lastName;
    private String manager_mobileNumber;

    List<BloodDetail> bloodDetailList=new ArrayList<>();
}

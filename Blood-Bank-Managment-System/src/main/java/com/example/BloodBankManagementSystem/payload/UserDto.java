package com.example.BloodBankManagementSystem.payload;

import com.example.BloodBankManagementSystem.entities.BloodType;
import com.example.BloodBankManagementSystem.entities.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

//this is what coming from the client-side and what we are sending back to the client-side
@Data
public class UserDto {

    @NotNull
    @NotEmpty(message = "Enter the username")
    private String username;

    @NotNull
    @Length(min = 6,message = "Length of the password should be atleast 6")
    @NotEmpty(message = "Enter the password")
    private String password;

    @NotNull
    @NotEmpty(message = "Enter the first-name")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Enter the last name")
    private String lastName;

    @NotNull
    @Email(message = "Enter the valid email address")
    private String email;

    @NotNull
    @Length(min = 10,max = 10,message = "mobile number should have 10 digits")
    @NotEmpty(message = "Enter the mobile number")
    private String mobileNumber;

    //this validation will be failed bcz spring-validator will not work on enum
    //@NotNull
    //@NotEmpty(message = "Choose the Blood Type")
    private BloodType bloodType;

    //we should also show the role of the user....
    //this field will be used when se send the data back to the client-side
    private Set<RoleDto> roles=new HashSet<>();
}

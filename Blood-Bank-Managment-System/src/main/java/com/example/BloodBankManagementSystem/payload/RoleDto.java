package com.example.BloodBankManagementSystem.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//actually not needed but will follow the rule not to expose our entity in our controllers
@Data
public class RoleDto {

    @NotNull
    @NotEmpty
    private String roleName;

    @NotNull
    @Length(min = 5,message = "Add some more details about the role")
    @NotEmpty
    private String roleDiscription;
}

package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean active;
    private Set<String> roles;

    // Additional fields needed by service
    private String city;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getName() { // used by UserServiceImpl
        return (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
    }

    public String getCity() {
        return city;
    }

}
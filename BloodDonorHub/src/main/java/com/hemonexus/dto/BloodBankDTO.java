package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodBankDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    private String address;

    private String city;
    private Long managerId;
    private String managerName;
    private String state;
    private String zipCode;
    private String country;
    private String phoneNumber;
    private String email;
    private String licenseNumber;
    private String operatingHours;

    // Additional fields needed by service
    private String website;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
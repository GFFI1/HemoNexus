package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO {
    private Long id;

    @NotNull
    private Long donorId;

    @NotNull
    private Long bloodBankId;

    @NotBlank
    private String bloodType;

    @NotNull
    private Integer quantityML;

    @NotNull
    @PastOrPresent
    private LocalDateTime donationDate;

    private String status;
    private String notes;
    private String donorName;
    private String bloodBankName;

    // Additional fields needed by service
    private Double quantity;
    private String hemoglobinLevel;
    private String pulseRate;
    private String bloodPressure;
    private String bodyTemperature;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
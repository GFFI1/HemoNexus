package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodInventoryDTO {
    private Long id;

    @NotNull
    private Long bloodBankId;

    @NotBlank
    private String bloodType;

    @NotNull
    @PositiveOrZero
    private Integer quantityML;

    @NotNull
    private LocalDateTime lastUpdated;

    @Positive
    private Integer minimumThresholdML;

    private LocalDateTime expirationDate;
    private String status;
    private String bloodBankName;

    // Additional fields needed by service
    private Double quantity;
    private Long donationId;
    private String storageLocation;
    private String batchNumber;
    private Boolean isAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
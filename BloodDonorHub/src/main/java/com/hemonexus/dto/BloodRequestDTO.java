package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequestDTO {
    private Long id;

    @NotNull
    private Long bloodBankId;

    @NotBlank
    private String bloodType;

    @NotNull
    @Positive
    private Integer quantityML;

    @NotNull
    private LocalDateTime requestDate;

    private LocalDateTime requiredBy;
    private String priority;
    private String patientName;

    @Size(max = 500)
    private String reason;

    private String status;
    private Integer fulfilledQuantityML;
    private String bloodBankName;
}
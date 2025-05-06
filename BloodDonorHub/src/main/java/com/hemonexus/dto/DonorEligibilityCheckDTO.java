package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorEligibilityCheckDTO {
    private Long id;

    @NotNull
    private Long donorId;

    private LocalDateTime checkDate;
    private boolean eligible;
    private String medicalNotes;
    private String checkedBy;
    private String donorName;
}
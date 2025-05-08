package com.hemonexus.dto;

import com.hemonexus.model.BloodRequest.Status;
import com.hemonexus.model.BloodRequest.Urgency;
import lombok.*;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequestDTO {
    private Long id;
    @NotNull
    private Long requesterId;
    @NotBlank
    private String bloodType;
    @Positive
    private Integer unitsNeeded;
    private Urgency urgencyLevel;
    private Status status;
    private String hospitalName;
    private String location;
}

package com.hemonexus.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private Long bloodBankId;
    private String bloodType;
    private int units;
    private String urgency;
}

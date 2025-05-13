package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserSummaryDTO {
    private Long id;
    private String name;
    private String city;
    private long total; // for donors = donations, for requesters = requests
}

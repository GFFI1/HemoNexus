package com.hemonexus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsDTO {
    private long donors;
    private long banks;
    private long units;
}

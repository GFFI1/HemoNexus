package com.hemonexus.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequesterDTO {
    private Long id;
    private String name; // full name for admin table
    private String city;
    private Integer totalRequests;

    public void setUsername(String name) {
        this.name = name;
    }
}

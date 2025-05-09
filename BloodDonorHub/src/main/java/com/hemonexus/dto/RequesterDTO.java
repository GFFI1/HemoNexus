package com.hemonexus.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequesterDTO {
    private Long id;
    private String username;
    private String city;
    private Integer totalRequests;
}

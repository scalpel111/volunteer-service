package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstitutionDTO {
    private Integer institutionId;
    private String institutionName;
    private String address;
    private Integer number;
    private Integer count;
}

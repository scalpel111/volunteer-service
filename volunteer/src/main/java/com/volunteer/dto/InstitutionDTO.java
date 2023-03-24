package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstitutionDTO {
    private String institutionName;
    private String address;
    private Integer number;
}

package com.volunteer.dto;

import com.volunteer.entity.InstitutionActivity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstitutionActivityDTO {
    private String theme;
    private InstitutionActivity institutionActivity;
}

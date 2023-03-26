package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityDTO {
    private String theme;
    private Integer recruitNumber;
    private String address;
}

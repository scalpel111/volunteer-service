package com.volunteer.dto;

import com.volunteer.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebActivityDTO {
    private Integer institutionId;
    private Activity activity;
}

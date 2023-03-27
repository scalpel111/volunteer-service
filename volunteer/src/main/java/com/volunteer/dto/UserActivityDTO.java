package com.volunteer.dto;

import com.volunteer.entity.UserActivity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserActivityDTO {
    private String theme;
    private double serviceTime;
    private UserActivity userActivity;
}

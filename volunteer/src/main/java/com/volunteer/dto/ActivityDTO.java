package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ActivityDTO {
    private Integer activityId;
    private String theme;
    private Integer recruitNumber;
    private String address;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tip;
}

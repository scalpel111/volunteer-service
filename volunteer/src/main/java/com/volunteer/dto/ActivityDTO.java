package com.volunteer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime endTime;
    private String tip;
}

package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String username;

    private String identityName;

    private String tellphone;

    private String declaration;

    private double serviceTime;
}

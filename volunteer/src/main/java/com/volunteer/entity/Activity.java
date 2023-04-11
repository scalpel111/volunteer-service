package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Activity对象", description="")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.AUTO)
    private Integer activityId;

    private String theme;
//    @TableId(value = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime startTime;
//    @TableId(value = "end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime endTime;

    private String address;

    private String req;

    private String description;

    private Integer recruitNumber;

    private String tip;


}

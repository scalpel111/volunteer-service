package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;


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
    private LocalDateTime startTime;

//    @TableId(value = "end_time")
    private LocalDateTime endTime;

    private String address;

    private String req;

    private String description;

    private Integer recruitNumber;

    private String tip;


}

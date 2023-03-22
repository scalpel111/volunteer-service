package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘毅晨
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Activity对象", description="")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.AUTO)
    private Integer activityId;

    private String theme;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String address;

    private String require;

    private String discription;

    private Integer recruitNumber;

    private String tip;


}

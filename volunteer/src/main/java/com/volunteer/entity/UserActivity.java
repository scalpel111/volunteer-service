package com.volunteer.entity;

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
@ApiModel(value="UserActivity对象", description="")
public class UserActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer activityId;

    private Integer userId;

    @ApiModelProperty(value = "0(已报名),1(未开始),2(进行中),3(已完成)")
    private Integer status;


}

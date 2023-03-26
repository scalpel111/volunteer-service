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
@ApiModel(value="UserInstitution对象", description="")
public class UserInstitution implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String openid;

    private Integer institutionId;

    @ApiModelProperty(value = "0(普通用户),1(管理员)")
    private Integer status;


}

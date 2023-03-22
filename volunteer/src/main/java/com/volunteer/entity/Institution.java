package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
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
@ApiModel(value="Institution对象", description="")
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "institution_id", type = IdType.AUTO)
    private Integer institutionId;

    private String institutionName;

    private String admin;

    private String admin Id;

    private String tellphone;

    private Blob proof;

    private Integer count;


}

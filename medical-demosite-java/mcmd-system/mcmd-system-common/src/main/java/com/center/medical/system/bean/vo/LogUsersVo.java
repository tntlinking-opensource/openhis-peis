package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据名称或输入码获取登录用户名
 */
@Data
public class LogUsersVo implements Serializable {
    private static final long serialVersionUID = 3006236648436751747L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "text")
    private String text;
}

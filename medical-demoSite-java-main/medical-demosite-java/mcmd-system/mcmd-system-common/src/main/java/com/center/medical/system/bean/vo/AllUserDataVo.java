package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有用户下拉返回数据
 */

@Data
public class AllUserDataVo implements Serializable {
    private static final long serialVersionUID = -833066771841666559L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "部门id")
    private String deptId;
}

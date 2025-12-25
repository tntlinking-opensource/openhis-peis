package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录页面分中心数据
 */
@Data
public class CenterListDataVo implements Serializable {
    private static final long serialVersionUID = -336911053797408391L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "父级分中心id")
    private String pid;

    @ApiModelProperty(value = "分中心名称")
    private String name;

    @ApiModelProperty(value = "是否默认分中心,只能有一个为1的分中心,当前数据库的分中心")
    private Integer isDefault;

}

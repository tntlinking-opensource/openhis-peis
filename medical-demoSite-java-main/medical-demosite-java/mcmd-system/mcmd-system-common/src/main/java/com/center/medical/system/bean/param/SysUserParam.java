package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2023-02-24 9:52
 * @description:
 */
@Data
public class SysUserParam {

    @ApiModelProperty(value = "分中心ID")
    private String cid;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "是否领导：0.否 1.是")
    private int isleader;

    @ApiModelProperty(value = "部分ID")
    private Long deptId;
}

package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取备单审批领导电话
 */
@Data
public class GetLeaderTelVo implements Serializable {
    private static final long serialVersionUID = 3397674605604842371L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String text;

}

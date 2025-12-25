package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-12-09 10:28
 * @description: 弃检职业项目的科室
 */
@Data
public class QjOsVo implements Serializable {
    private static final long serialVersionUID = -2142501364038571023L;

    @ApiModelProperty(value = "科室编号")
    private String id;

    @ApiModelProperty(value = "收费项目名称，多个以英文逗号隔开")
    private String feeitemNames;
}

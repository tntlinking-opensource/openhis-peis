package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 有弃检职业项目的科室
 */
@Data
public class DiscardDto implements Serializable {

    private static final long serialVersionUID = 8472931802815064872L;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

}

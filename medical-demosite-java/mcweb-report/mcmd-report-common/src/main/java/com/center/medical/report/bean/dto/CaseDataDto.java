package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取 收费项目返回数据
 */
@Data
public class CaseDataDto implements Serializable {
    private static final long serialVersionUID = -3838419709970042874L;

    @ApiModelProperty(value = "收费项目打印名称")
    private String feeName;

    @ApiModelProperty(value = "收费项目名称")
    private String itemName;

    @ApiModelProperty(value = "描述")
    private String describe;
}

package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * C13读卡数据
 */
@Data
public class C13NkdataDto implements Serializable {
    private static final long serialVersionUID = 5904072505734880195L;

    @ApiModelProperty(value = "科室编码，对应原系统的deptId")
    private String id;
}

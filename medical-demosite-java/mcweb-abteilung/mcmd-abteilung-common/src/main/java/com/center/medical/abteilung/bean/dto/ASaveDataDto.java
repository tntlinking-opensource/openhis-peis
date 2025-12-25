package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小结数据
 */
@Data
public class ASaveDataDto implements Serializable {
    private static final long serialVersionUID = 1976645102706306824L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "检查项目ID")
    private String itemId;

    @ApiModelProperty(value = "复查项目")
    private String itemName;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "检查描述")
    private String inspectDescribe;

    @ApiModelProperty(value = "收费项目Id")
    private String feeId;

    @ApiModelProperty(value = "用于存放该检查项目下的所有体证词所拼接的字符串。")
    private String signList;


}

package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TJ_RenD_Data
 */
@Data
public class TjRenDData implements Serializable {

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "检查项目ID")
    private String itemId;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "收费项目打印名称")
    private String feeName;

    @ApiModelProperty(value = "收费项目Id")
    private String feeId;

    @ApiModelProperty(value = "用于存放该检查项目下的所有体证词所拼接的字符串。")
    private String signList;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "科室编码，对应原系统的deptId")
    private String deptNo;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "检查人")
    private String inspectName;
}

package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检人科室总结数据
 */
@Data
public class TjRenKeShiZj implements Serializable {
    private static final long serialVersionUID = 5757677584570326924L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "检查项目ID")
    private String itemId;

    @ApiModelProperty(value = "检查结果描述")
    private String examresultdesc;

    @ApiModelProperty(value = "检查结果总结")
    private String examresultsummary;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "录入时间")
    private Date writeTime;

    @ApiModelProperty(value = "项目名称")
    private String itemName;


}

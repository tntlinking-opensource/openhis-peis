package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验报告配置
 */
@Data
public class InspectionReportConfig implements Serializable {

    private static final long serialVersionUID = -4099784636135559180L;

    @ApiModelProperty(value = "中心-显示在系统页面上的名字")
    private String centerText;

    @ApiModelProperty(value = "中心-报告抬头")
    private String centerId;

    @ApiModelProperty(value = "中心-报告中是否显示审核人、检验者的姓名   1显示 ")
    private String centerSign;

    @ApiModelProperty(value = "客户-显示在系统页面上的名字")
    private String customText;

    @ApiModelProperty(value = "客户-报告抬头")
    private String customId;

    @ApiModelProperty(value = "客户-报告中是否显示审核人、检验者的姓名   1显示 ")
    private String customSign;

    @ApiModelProperty(value = "送检单位")
    private String inspectionUnit;

}

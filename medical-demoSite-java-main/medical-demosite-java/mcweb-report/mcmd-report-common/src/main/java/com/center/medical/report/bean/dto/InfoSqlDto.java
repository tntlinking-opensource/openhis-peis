package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验报告数据
 */
@Data
public class InfoSqlDto implements Serializable {
    private static final long serialVersionUID = 8916348923785584586L;

    @ApiModelProperty(value = "打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "LIS样本编号")
    private String lisybbh;

    @ApiModelProperty(value = "审核时间")
    private String auditDate;

    @ApiModelProperty(value = "收样时间（虹桥lis）")
    private String receiveDate;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "检查人")
    private String inspectName;

    @ApiModelProperty(value = "检查项目英文名称")
    private String examitemNameeng;

    @ApiModelProperty(value = "状态")
    private String status;
}

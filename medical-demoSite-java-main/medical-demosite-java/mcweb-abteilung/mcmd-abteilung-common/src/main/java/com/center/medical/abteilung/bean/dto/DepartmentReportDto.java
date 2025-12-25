package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询科室报告数据
 */
@Data
public class DepartmentReportDto implements Serializable {
    private static final long serialVersionUID = -5343457656682275851L;

    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;

    @ApiModelProperty(value = "检查人姓名")
    private String rummagerName;

    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "审核人医生签字")
    private String auditSignPic;

    @ApiModelProperty(value = "检查人医生签字")
    private String rummagerSignPic;

}

package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室检验科报告 返回LIS结果数据
 */
@Data
public class GetInspectReportVo implements Serializable {
    private static final long serialVersionUID = 6073941753308846146L;


    @ApiModelProperty(value = "检查项目名称")
    private String itemName;

    @ApiModelProperty(value = "审核时间")
    private String date;

    @ApiModelProperty(value = "检查人")
    private String pName;

    @ApiModelProperty(value = "检验项目")
    private String item;

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "参考值")
    private String unit;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "小结")
    private String consult;

    @ApiModelProperty(value = "审核人姓名")
    private String auditName;
}

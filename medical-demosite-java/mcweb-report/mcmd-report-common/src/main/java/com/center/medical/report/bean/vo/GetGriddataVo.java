package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取分科校验数据
 */
@Data
public class GetGriddataVo implements Serializable {

    private static final long serialVersionUID = -4407322094404600173L;


    @ApiModelProperty(value = "体检者记录ID")
    private String id;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examFeeItem;

    @ApiModelProperty(value = "检查项目名称")
    private String examItemNameR;

    @ApiModelProperty(value = "LIS代码")
    private String lisCode;

    @ApiModelProperty(value = "检查项目体征: 数字")
    private String examitemvaluesnumber;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "检查人")
    private String inspectName;

    @ApiModelProperty(value = "检查时间")
    private Date examDateTime;

    @ApiModelProperty(value = "检查项目体征: 简单描述(结果)")
    private String examitemvaluesshort;

    @ApiModelProperty(value = "报告结果")
    private String reportRange;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "审核人")
    private String auditName;

    @ApiModelProperty(value = "LIS范围/报告范围 低")
    private Double reflow;

    @ApiModelProperty(value = "LIS范围/报告范围 高")
    private Double refhigh;

    @ApiModelProperty(value = "LIS样本编号")
    private Double lisybbh;

    @ApiModelProperty(value = "检查项目ID")
    private String idExamitem;

    @ApiModelProperty(value = "result")
    private String result;

}

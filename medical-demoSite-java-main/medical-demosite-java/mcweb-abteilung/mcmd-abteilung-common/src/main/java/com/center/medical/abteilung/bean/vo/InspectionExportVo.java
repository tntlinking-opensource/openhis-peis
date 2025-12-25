package com.center.medical.abteilung.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验数据导出返回数据
 */
@Data
public class InspectionExportVo implements Serializable {

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "单位")
    @ApiModelProperty(value = "单位")
    private String orgName;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;


    @Excel(name = "检验项目")
    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @Excel(name = "数值")
    @ApiModelProperty(value = "数值")
    private String examitemvaluesnumber;


    @Excel(name = "结果")
    @ApiModelProperty(value = "结果")
    private String examitemvaluesshort;

    @Excel(name = "正常")
    @ApiModelProperty(value = "状态")
    private String status;

    @Excel(name = "单位")
    @ApiModelProperty(value = "单位")
    private String units;

    @Excel(name = "LIS范围")
    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @Excel(name = "检验师")
    @ApiModelProperty(value = "检查人")
    private String inspectName;

    @Excel(name = "审核医师")
    @ApiModelProperty(value = "审核人")
    private String auditName;

    @Excel(name = "检验时间")
    @ApiModelProperty(value = "检查时间")
    private Date examDateTime;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;
}

package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出收费项目
 */
@Data
public class ExportItemsVo implements Serializable {
    private static final long serialVersionUID = 2712507010566061466L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;


    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @Excel(name = "原始单价")
    @ApiModelProperty(value = "价格")
    private Double price;

    @Excel(name = "优惠单价")
    @ApiModelProperty(value = "实付价格")
    private Double factprice;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "加项医师")
    @ApiModelProperty(value = "加项医师名称")
    private String jxysmc;

    @Excel(name = "登记" ,readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @Excel(name = "退项" ,readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "换项")
    private Integer changeItem;

    @Excel(name = "已收" ,readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否已收费：0.否 1.是")
    private Integer fFeecharged;

    @Excel(name = "已发" ,readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "已发：0.否 1.是")
    private Integer fLabsendtolis;

    @Excel(name = "已检" ,readConverterExp = "null=未检,0=未检,1=已检")
    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @Excel(name = "弃检" ,readConverterExp = "null=未检,0=未检,1=已检")
    @ApiModelProperty(value = "弃检")
    private Integer fGiveup;

    @Excel(name = "迟检" ,readConverterExp = "null=未检,0=未检,1=已检")
    @ApiModelProperty(value = "迟检")
    private String FDelayed;

    @Excel(name = "补检" ,readConverterExp = "null=未检,0=未检,1=已检")
    @ApiModelProperty(value = "补检状态 0: 未补检 1：已补检")
    private Integer fTransferedhl7;

    @Excel(name = "科室")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @Excel(name = "登记人")
    @ApiModelProperty(value = "登记员名称")
    private String doctorregName;

    @Excel(name = "收费时间")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;
}

package com.center.medical.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出体检者收费项目列表 返回数据
 */
@Data
public class ExportItemsVo implements Serializable {
    private static final long serialVersionUID = 253577771708457543L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "原始单价")
    @ApiModelProperty(value = "价格")
    private String price;

    @Excel(name = "优惠单价")
    @ApiModelProperty(value = "实付价格")
    private Double factprice;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "加项医师")
    @ApiModelProperty(value = "加项医师名称")
    private String jxysName;

    @Excel(name = "登记", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private Integer fRegistered;

    @Excel(name = "退项", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "换项")
    private Integer changeItem;

    @Excel(name = "已收", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "已收费")
    private Integer fFeecharged;

    @Excel(name = "已发", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "已发：0.否 1.是")
    private Integer fLabsendtolis;

    @Excel(name = "已检", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "0：未检；1：已检；")
    private Integer fExaminated;

    @Excel(name = "弃检", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "弃检 0 或者null：未弃检 1：弃检")
    private Integer fGiveup;

    @Excel(name = "迟检", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "迟检")
    private Integer FDelayed;

    @Excel(name = "补检", readConverterExp = "0=否,1=是,null=否")
    @ApiModelProperty(value = "补检状态 0: 未补检 1：已补检")
    private Integer fTransferedhl7;

    @Excel(name = "科室")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @Excel(name = "登记人")
    @ApiModelProperty(value = "登记员名称")
    private String doctorregName;

    @Excel(name = "收费时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;
}

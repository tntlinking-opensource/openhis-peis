package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 应收预收函证 分页返回数据
 */
@Data
public class ReceiptLetterVo implements Serializable {
    private static final long serialVersionUID = -5322567324623687669L;

    @Excel(name = "客户全程")
    @ApiModelProperty(value = "单位")
    private String khdwmc;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "新增备单总金额")
    @ApiModelProperty(value = "新增备单总金额")
    private Double totalMoney;

    @Excel(name = "备单套餐名称")
    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @Excel(name = "是否统收")
    @ApiModelProperty(value = "是否统收")
    private String isTong;

    @Excel(name = "新增备单客单价（元）")
    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @Excel(name = "计划来检人数（人）")
    @ApiModelProperty(value = "计划人数")
    private Double numberOfPeople;

    @Excel(name = "实际到检人数（人）")
    @ApiModelProperty(value = "实际人数")
    private Double numberOfPeopleRegistered;


    @Excel(name = "实际到检金额（元）")
    @ApiModelProperty(value = "实际人数,只有导出有这个字段")
    private Double ActualInspectionAmount;


}

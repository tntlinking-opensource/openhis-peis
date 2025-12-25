package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取套餐 返回数据
 */
@Data
public class GetPackageVo implements Serializable {
    private static final long serialVersionUID = 5684443610518477045L;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "收费项目集合")
    private List<ItemDataVo> itemData;

    @ApiModelProperty(value = "总成本")
    private Double totalCost;

    @ApiModelProperty(value = "总售价")
    private Double totalPrice;

    @ApiModelProperty(value = "百分比")
    private Double percentage;

    @ApiModelProperty(value = "总优惠价")
    private Double TotalDiscountedPrice;

    @ApiModelProperty(value = "变动成本率")
    private Double variableCostRate;

}

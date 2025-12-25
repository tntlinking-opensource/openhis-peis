package com.center.medical.data.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 按性别 职业体检类型  接害因素 获取最小套餐收费项目 ID 价格
 */
@Data
public class ComboDataDto implements Serializable {
    private static final long serialVersionUID = -2410453488599196537L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @ApiModelProperty(value = "禁止打折字段，1禁止打折")
    private Integer fDiscountdisabled;
}

package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量设置统收限额 保存参数
 */
@Data
public class TsLimitEditParam implements Serializable {
    private static final long serialVersionUID = 5498578106866820258L;

    @ApiModelProperty(value = "订单ID")
    private String order;

    @ApiModelProperty(value = "检查套餐id")
    private String jctcId;

    @ApiModelProperty(value = "开始年龄")
    private Integer ageStart;

    @ApiModelProperty(value = "结束年龄")
    private Integer ageEnd;

    @ApiModelProperty(value = "性别,0男1女2通用")
    private String gender;

    @ApiModelProperty(value = "充值金额")
    private String money;
}

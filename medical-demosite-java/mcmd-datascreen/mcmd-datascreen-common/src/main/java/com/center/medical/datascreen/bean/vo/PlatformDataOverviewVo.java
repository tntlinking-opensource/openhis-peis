package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检中心概况
 * @author xhp
 * @since 2023-05-30 14:17
 */
@Data
public class PlatformDataOverviewVo implements Serializable {
    @ApiModelProperty(value = "总人数")
    private int totalNumber;
    @ApiModelProperty(value = "营业额")
    private double totalAmount;
    @ApiModelProperty(value = "折扣率")
    private String discountRate;
}

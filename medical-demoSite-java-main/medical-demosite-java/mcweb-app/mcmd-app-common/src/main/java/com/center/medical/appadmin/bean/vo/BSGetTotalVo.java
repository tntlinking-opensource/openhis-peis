package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务结算表 获取总额
 */
@Data
public class BSGetTotalVo implements Serializable {
    private static final long serialVersionUID = -1738889043433487557L;

    @ApiModelProperty(value = "支付积分")
    private Double payScore;

    @ApiModelProperty(value = "支付金额")
    private Double payAmount;

}

package com.center.medical.bean.event;

import com.center.medical.bean.param.SuiXingTradeRefundParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付退款
 */
@Data
@AllArgsConstructor
public class SuiXingRefundEvent implements Serializable {

    private static final long serialVersionUID = 819245663510240946L;

    @ApiModelProperty(value = "参数")
    private SuiXingTradeRefundParam param;
}

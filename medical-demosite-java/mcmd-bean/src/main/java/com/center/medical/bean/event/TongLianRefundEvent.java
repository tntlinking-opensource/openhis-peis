package com.center.medical.bean.event;

import com.center.medical.bean.param.TongLianRefundParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 通联支付退款
 */
@Data
@AllArgsConstructor
public class TongLianRefundEvent implements Serializable {
    private static final long serialVersionUID = -3699383542810628064L;

    @ApiModelProperty(value = "参数")
    private TongLianRefundParam param;
}

package com.center.medical.outreach.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 合作单位开放接口查询报告列表参数
 * @author: 路飞船长
 * @date: 2024/11/6 09:57
 * @description:
 */
@Data
public class CooOrderReportParam implements Serializable {

    private static final long serialVersionUID = -1676686631468017491L;
    
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "手机号")
    private String phone;
}

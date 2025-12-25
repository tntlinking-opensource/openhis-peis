package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 平安对账单 分页 参数
 */
@Data
public class PingAnPageParam implements Serializable {
    private static final long serialVersionUID = 5347778530921918338L;

    @ApiModelProperty(value = "分中心ID")
    private String center;

    @ApiModelProperty(value = "订单号 集合")
    private List<String> order;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "客户单位团体号")
    private String intId;
}

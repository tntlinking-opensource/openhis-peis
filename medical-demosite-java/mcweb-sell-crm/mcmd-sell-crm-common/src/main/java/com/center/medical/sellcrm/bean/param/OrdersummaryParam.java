package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-23 18:43
 * @description:
 */
@Data
public class OrdersummaryParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -154062369172446309L;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "销售经理")
    private String xsjlid;
}

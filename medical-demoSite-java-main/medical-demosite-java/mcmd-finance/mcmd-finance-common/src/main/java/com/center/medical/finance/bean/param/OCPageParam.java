package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 团检订单折扣 分页 参数
 */
@Data
public class OCPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 1258515889884570526L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "订单折扣")
    private Double ddzk;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;


}

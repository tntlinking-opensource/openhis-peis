package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据key模糊查询获取所有订单数据
 */
@Data
public class AllOrderDataVo implements Serializable {
    private static final long serialVersionUID = 5540330301548043455L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;
}

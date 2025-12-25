package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取订单信息 返回数据
 */
@Data
public class OrderListDataVo implements Serializable {
    private static final long serialVersionUID = 3590632230513076889L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "订单号")
    private String ddh;
}

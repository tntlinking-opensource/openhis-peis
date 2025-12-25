package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/5/20 11:06
 * @description: 团检加项参数
 */
@Data
public class OrderAddItemParam implements Serializable {
    private static final long serialVersionUID = -8773537962925322180L;

    @ApiModelProperty(value = "收费项目ID")
    private  String itemId;

    @ApiModelProperty(value = "订单号")
    private  String ddh;

    @ApiModelProperty(value = "套餐ID")
    private String tcId;

    @ApiModelProperty(value = "更新体检者：0未检的，1已检的，2全部")
    private Integer fRegistered;

    @ApiModelProperty(value = "价格")
    private Double price;

//    @ApiModelProperty(value = "操作类型：0加项 1.减项")
//    private Double falg;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;
}

package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户订单信息
 *
 * @author 路飞船长
 * @since 2024-11-06 15:14:06
 */
@Data
public class CustomerOrderVo implements Serializable {

    private static final long serialVersionUID = -5620924216593279498L;

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "订单名称")
    private String orderName;

    @ApiModelProperty(value = "销售经理")
    private String sale;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "客户单位名称")
    private String customerName;

    @ApiModelProperty(value = "客户单位联系人")
    private String customerContact;

    @ApiModelProperty(value = "客户单位联系电话")
    private String customerPhone;

}

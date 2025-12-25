package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单分中心数据
 */
@Data
public class OrderFzxDto implements Serializable{
    private static final long serialVersionUID = 5405681795263361118L;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "分中心地址")
    private String address;

    @ApiModelProperty(value = "分中心联系电话")
    private String tel;
}

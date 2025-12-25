package com.center.medical.appadmin.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务结算表 参数
 */
@Data
public class BsSettlementParam implements Serializable {
    private static final long serialVersionUID = 2710676037564797276L;


    @ApiModelProperty(value = "分中心id")
    private String fzxId;


    @ApiModelProperty(value = "第三方系统的订单号")
    private String bizOrderNo;


    @ApiModelProperty(value = "开始时间")
    private Date startTime;


    @ApiModelProperty(value = "结束时间")
    private Date endTime;


    @ApiModelProperty(value = "手机号")
    private String userMobile;
}

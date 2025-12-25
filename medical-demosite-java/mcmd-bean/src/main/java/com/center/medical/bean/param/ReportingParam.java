package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通联支付报备设备参数
 * https://aipboss.allinpay.com/know/devhelp/main.php?pid=15#mid=948
 * https://prodoc.allinpay.com/doc/267/
 */
@Data
public class ReportingParam implements Serializable {

    @ApiModelProperty(value = "终端号")
    private String termno;

    @ApiModelProperty(value = "设备类型")
    private String devicetype;

    @ApiModelProperty(value = "操作类型")
    private String operation;

    @ApiModelProperty(value = "终端状态")
    private String termstate;

    @ApiModelProperty(value = "终端地址")
    private String termaddress;
}

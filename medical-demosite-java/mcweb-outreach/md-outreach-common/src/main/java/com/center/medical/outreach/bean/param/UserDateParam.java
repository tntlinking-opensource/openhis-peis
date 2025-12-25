package com.center.medical.outreach.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 第三方合作客户接口预约获取体检者数据参数
 * @author: 路飞船长
 * @date: 2024/11/6 09:57
 * @description: 第三方合作客户接口预约获取体检者数据参数
 */
@Data
public class UserDateParam implements Serializable {
    private static final long serialVersionUID = -4874168632628312714L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "手机号")
    private String phone;
}

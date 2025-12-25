package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/12/10 06:14
 * @description: 获取体检者体检结果的请求参数
 */
@Data
public class UserExamDataParam implements Serializable {
    private static final long serialVersionUID = 4588934860068907436L;

    /**
     * 订单ID，多个以英文逗号隔开
     */
    @ApiModelProperty(value = "订单ID", required = true)
    private String orderId;

    /**
     * 本次请求的唯一标识，调用回调接口推送数据时会携带该参数，用于标记是本次请求的结果数据
     */
    @ApiModelProperty(value = "请求标识")
    private String flag;

    /**
     * 回调地址，推送请求结果的地址
     */
    @ApiModelProperty(value = "回调地址")
    private String callBack;
}

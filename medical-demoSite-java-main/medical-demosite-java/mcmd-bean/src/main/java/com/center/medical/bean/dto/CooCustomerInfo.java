package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 第三方授权登录信息
 * @author: 路飞船长
 * @date: 2024/11/20 05:42
 * @description: 记录账号密码登录成功客户账号相关的信息
 */
@Data
public class CooCustomerInfo implements Serializable {
    private static final long serialVersionUID = -5819376180763906240L;

    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private String sourceId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String typeName;

    /**
     * 客户账号
     */
    @ApiModelProperty(value = "客户账号")
    private String account;

    /**
     * 合作第三方客户设置
     */
    @ApiModelProperty(value = "合作第三方客户设置")
    private String setting;

    /**
     * 授权码
     */
    @ApiModelProperty(value = "授权码")
    private String authCode;

    /**
     * 业务标识
     */
    @ApiModelProperty(value = "业务标识")
    private String bsFlag;

    /**
     * 授权信息参数设置对象
     */
    @ApiModelProperty(value = "授权信息参数设置对象")
    private String codeSetting;

    /**
     * 已授权的业务标识
     */
    @ApiModelProperty(value = "已授权的业务标识")
    private List<String> bsFlagList;
}

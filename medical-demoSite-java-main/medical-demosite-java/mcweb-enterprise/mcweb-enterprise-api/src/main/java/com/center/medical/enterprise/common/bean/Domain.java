/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 域名配置信息
 *
 * @author lgh
 */
@Data
public class Domain implements Serializable {

    private static final long serialVersionUID = -2993759767981355661L;

    @ApiModelProperty(value = "沃德体检系统平台端外联系统地址")
    private String mdPlatformOutreach;

    @ApiModelProperty(value = "资源地址")
    private String mdResources;

    @ApiModelProperty(value = "报告地址")
    private String reportAddress;

    @ApiModelProperty(value = "沃德体检系统平台线上地址")
    private String onlineAddress;

}

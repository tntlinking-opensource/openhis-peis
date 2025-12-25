/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 域名配置信息
 *
 * @author 路飞
 */
@Data
public class Domain implements Serializable {

    private static final long serialVersionUID = -425937536945644271L;

    @ApiModelProperty(value = "APP端后台接口域名")
    private String appDomain;

    @ApiModelProperty(value = "平台端后台接口域名")
    private String platformDomain;

    @ApiModelProperty(value = "本地主系统后台接口域名")
    private String lcDomain;

    @ApiModelProperty(value = "平台端资源域名")
    private String rsPfDomain;

    @ApiModelProperty(value = "中心体检系统端资源地址")
    private String rsLcDomain;

    @ApiModelProperty(value = "分中心外联系统接口地址")
    private String outreachLcDomain;

    @ApiModelProperty(value = "平台外联系统接口地址")
    private String outreachPfDomain;

    @ApiModelProperty(value = "分中心日志系统接口地址")
    private String logLcDomain;

    @ApiModelProperty(value = "平台外日志统接口地址")
    private String logPfDomain;

    @ApiModelProperty(value = "lis外联系统接口地址")
    private String lisDomain;

    @ApiModelProperty(value = "第三方pacs接口地址")
    private String pacsDomain;

    @ApiModelProperty(value = "是否线上系统：0否 1是")
    private Integer isOnline;

    @ApiModelProperty(value = "各分中心自有系统后台接口域名")
    private String branchOwnDomain;

    @ApiModelProperty(value = "职业数据上传接口地址")
    private String professionDomain;

    @ApiModelProperty(value = "老系统线上接口地址")
    private String oldSystemPfDomain;

    @ApiModelProperty(value = "老系统线下接口地址")
    private String oldSystemLcDomain;
}

/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OSS配置信息
 *
 * @author LGH
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AliOss extends SwitchBaseModel {

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;
}

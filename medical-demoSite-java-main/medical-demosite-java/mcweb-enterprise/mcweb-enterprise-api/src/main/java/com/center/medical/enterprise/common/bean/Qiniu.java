/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.bean;

import com.center.medical.enterprise.common.enums.QiniuZone;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 七牛云存储配置信息
 *
 * @author lgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Qiniu extends SwitchBaseModel {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private QiniuZone zone;
}

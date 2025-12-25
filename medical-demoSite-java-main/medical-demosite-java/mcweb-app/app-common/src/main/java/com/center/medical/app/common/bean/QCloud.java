/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 腾讯云cos存储配置信息
 * @author lgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QCloud extends SwitchBaseModel {

	private String secretId;

	private String secretKey;

	private String region;

	private String bucketName;
}

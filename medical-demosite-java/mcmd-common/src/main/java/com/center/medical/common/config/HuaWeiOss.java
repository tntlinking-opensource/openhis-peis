/*
 * Copyright (c) 2021-2999 沃德国际 All rights reserved.
 *
 * https://www.maiwd.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.config;


import lombok.Data;

/**
 * OBS配置信息
 * @author LGH
 */
@Data
public class HuaWeiOss {

	private String  bucketName;

	private String  accessKeyId;

	private String  secretAccessKey;

	private String  endpoint;

	private Boolean isOpen;
}

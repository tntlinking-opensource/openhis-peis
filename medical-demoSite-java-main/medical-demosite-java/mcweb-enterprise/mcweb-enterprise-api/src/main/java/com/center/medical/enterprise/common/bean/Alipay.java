/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.bean;

import lombok.Data;

/**
 * 支付宝配置
 * @author lgh
 */
@Data
public class Alipay {

	private String appId;

	/**
	 * 应用公钥证书
	 */
	private String appCertPath;

	/**
	 * 支付宝公钥证书
	 */
	private String alipayCertPath;

	/**
	 * 支付宝根证书
	 */
	private String alipayRootCertPath;

	/**
	 * 应用私钥
	 */
	private String appPrivateKey;
}

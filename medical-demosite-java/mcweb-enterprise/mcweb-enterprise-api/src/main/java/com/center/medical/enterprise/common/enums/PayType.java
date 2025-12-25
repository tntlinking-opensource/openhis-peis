/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.enums;

/**
 * 支付类型
 * @author yami
 */
public enum PayType {

	/** 积分支付*/
	SCOREPAY(0,"积分支付"),

	/** 微信支付*/
	WECHATPAY(1,"小程序支付"),

	/** 支付宝*/
	ALIPAY(2,"支付宝"),

	/** 微信扫码支付*/
	WECHATPAY_SWEEP_CODE(3,"微信扫码支付"),

	/** 微信H5支付*/
	WECHATPAY_H5(4,"微信H5支付"),

	/** 微信公众号*/
	WECHATPAY_MP(5,"微信公众号支付"),

	/** 支付宝H5支付*/
	ALIPAY_H5(6,"支付宝H5支付"),

	/** 支付宝APP支付*/
	ALIPAY_APP(7,"支付宝APP支付"),

	/** 微信APP支付*/
	WECHATPAY_APP(8,"微信APP支付"),

	/** 余额支付*/
	BALANCE(9,"余额支付"),

	/** paypal支付 */
	PAYPAL(10,"paypal支付"),

	/** 随行支付 */
	SUIXINGPAL(11,"随行支付");



	private Integer num;

	private String payTypeName;

	public Integer value() {
		return num;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	PayType(Integer num,String payTypeName){
		this.num = num;
		this.payTypeName = payTypeName;
	}

	public static PayType instance(Integer value) {
		PayType[] enums = values();
		for (PayType statusEnum : enums) {
			if (statusEnum.value().equals(value)) {
				return statusEnum;
			}
		}
		return null;
	}


	public static String getNameByNum(int num) {
		for (PayType sp : PayType.values()) {
			if (sp.num == num) {
				return sp.payTypeName;
			}
		}
		return "";
	}
}

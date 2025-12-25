package com.center.medical.outside.config;

/**
 * @author: 路飞
 * @date: 2023-04-04 11:35
 * @description: 外联系统常量
 */
public class Constants {


    /**
     * 合作单位登录授权：用于aes签名的key，16位
     */
    public static final String tokenSignKey= "343354b655c9cefa";
    /**
     * 合作单位登录授权：半小时内最多错误10次
     */
    public static final int TIMES_CHECK_INPUT_PASSWORD_NUM = 10;
    /**
     * 合作单位登录授权：检查用户输入错误的验证码次数key的前缀
     */
    public static final String CHECK_VALID_CODE_NUM_PREFIX = "checkUserInputErrorPassword_";
    /**
     * 合作单位登录授权：生成授权令牌token有效期：2个小时
     */
    public static final Long ACCESS_TOKEN_EXPIRES_TIME = 2*60*60L;

}

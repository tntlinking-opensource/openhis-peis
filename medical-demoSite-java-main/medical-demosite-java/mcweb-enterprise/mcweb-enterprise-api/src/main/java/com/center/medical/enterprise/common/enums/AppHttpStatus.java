/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.enums;

/**
 * 与前端进行特殊交互需要使用的状态码，由于小程序需要，所以状态码只能为3位数字，并且不能与正常的http状态码冲突
 *
 * @author LGH
 */
public enum AppHttpStatus {
    /**
     * 客户端看到401状态码时，应该重新登陆
     */
    UNAUTHORIZED(401, "未授权"),

    COUPONCANNOTUSETOGETHER(601, "优惠券不能共用"),

    SENSITIVE_WORD(400, "存在敏感词，请重新输入"),

    BAD_REQUEST(400, "参数列表错误（缺少，格式不匹配）"),

    CONFLICT(409, "资源冲突，或者已被锁定"),

    SOCIAL_ACCOUNT_NOT_BIND(475, "未绑定社交账号"),
    ACCOUNT_NOT_REGISTER(476, "账号未注册"),

    SUCCESS(200, "请求成功"),

    ;


    private final int value;

    private final String msg;


    AppHttpStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the msg of this status code.
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return this.value + " " + name();
    }


    public static AppHttpStatus valueOf(int statusCode) {
        AppHttpStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("没有找到该Http状态码包含状态 [" + statusCode + "]");
        }
        return status;
    }

    public static AppHttpStatus resolve(int statusCode) {
        for (AppHttpStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }


}

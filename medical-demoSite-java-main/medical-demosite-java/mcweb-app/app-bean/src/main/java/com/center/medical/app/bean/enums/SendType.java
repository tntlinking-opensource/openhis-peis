/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.enums;

/**
 * 1.订单催付 2.付款成功通知 3.商家同意退款 4.商家拒绝退款 5.核销提醒  6.发货提醒  7.拼团失败提醒 8.拼团成功提醒 9.拼团开团提醒 10.开通会员提醒
 * 11.余额退款成功提醒
 * 101.退款临近超时提醒 102.确认收货提醒 103.买家发起退款提醒 104.买家已退货提醒
 *
 * @author lhd
 * @date 2020-08-27 17:55:57
 */
public enum SendType {
    /**
     * 自定义消息
     */
    CUSTOMIZE(0, 1, "自定义消息"),
    /**
     * 订单催付
     */
    PRESS_PAY(1, 2, "订单催付"),

    /**
     * 付款成功通知
     */
    PAY_SUCCESS(2, 2, "付款成功通知"),

    /**
     * 商家同意退款
     */
    AGREE_REFUND(3, 2, "商家同意退款"),

    /**
     * 商家拒绝退款
     */
    REFUSE_REFUND(4, 2, "商家拒绝退款"),

    /**
     * 核销提醒
     */
    WRITE_OFF(5, 2, "核销提醒"),

    /**
     * 发货提醒
     */
    DELIVERY(6, 2, "发货提醒"),

    /**
     * 拼团失败提醒
     */
    GROUP_FAIL(7, 2, "拼团失败提醒"),

    /**
     * 拼团成功提醒
     */
    GROUP_SUCCESS(8, 2, "拼团成功提醒"),

    /**
     * 拼团开团提醒
     */
    GROUP_START(9, 2, "拼团开团提醒"),

    /**
     * 会员升级通知
     */
    MEMBER(10, 1, "会员升级通知"),

    /**
     * 退款临近超时提醒
     */
    REFUND_OUT_TIME(11, 2, "退款临近超时提醒"),
    /**
     * 用户注册验证码
     */
    REGISTER(12, 1, "用户注册验证码"),
    /**
     * 发送登录验证码
     */
    LOGIN(13, 1, "发送登录验证码"),
    /**
     * 修改密码验证码
     */
    UPDATE_PASSWORD(14, 1, "修改密码验证码"),
    /**
     * 身份验证验证码
     */
    VALID(15, 1, "身份验证验证码"),
    /**
     * 订单退款到余额提醒
     */
    ORDER_REFUND_TO_BALANCE(16, 1, "订单退款到余额提醒"),

    /**
     * 确认收货提醒
     */
    RECEIPT_ORDER(102, 2, "确认收货提醒"),

    /**
     * 买家发起退款提醒
     */
    LAUNCH_REFUND(103, 2, "买家发起退款提醒"),

    /**
     * 买家已退货提醒
     */
    RETURN_REFUND(104, 2, "买家已退货提醒");

    private Integer value;
    /**
     * 1为全部平台发送的消息，2为根据情况,3为单独站内信通知
     */
    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    SendType(Integer value, Integer type, String desc) {
        this.value = value;
        this.type = type;
        this.desc = desc;
    }

    public static SendType instance(Integer value) {
        SendType[] enums = values();
        for (SendType statusEnum : enums) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}

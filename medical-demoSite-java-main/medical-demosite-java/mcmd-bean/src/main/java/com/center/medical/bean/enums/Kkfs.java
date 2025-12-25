package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 8.复查额度
 */
public enum Kkfs {

    /**
     * 体检卡
     */
    TJK(1, "体检卡"),

    /**
     * 会员卡积分
     */
    HYKJF(2, "会员卡积分"),

    /**
     * 微信扫码枪
     */
    WXPAY(3, "微信扫码枪"),

    /**
     * 支付宝扫码枪
     */
    ALIPAY(4, "支付宝扫码枪"),

    /**
     * 会员卡余额
     */
    HYKYE(5, "会员卡余额"),

    /**
     * 家庭卡余额
     */
    JTKYE(6, "家庭卡余额"),

    /**
     * 家庭卡积分
     */
    JTKJF(7, "家庭卡积分"),

    /**
     * 复查额度
     */
    FCED(7, "复查额度"),

    /**
     * 通联支付
     */
    TONGLIAN(9, "通联支付"),

    /**
     * 通联支付
     */
    SUIXING(10, "随行付"),
    /**
     * 通联支付2
     */
    TONGLIAN2(11, "通联支付2"),;

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    public String gName() {
        return name;
    }

    Kkfs(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static Kkfs instance(Integer value) {
        Kkfs[] enums = values();
        for (Kkfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        Kkfs[] enums = values();
        for (Kkfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }


    public static Integer getValueByName(String name) {
        Kkfs[] enums = values();
        System.out.println("类型列表：" + JSONUtil.toJsonStr(enums));
        for (Kkfs enmu : enums) {
            if (enmu.gName().equals(name)) {
                return enmu.value();
            }
        }
        return null;
    }

}

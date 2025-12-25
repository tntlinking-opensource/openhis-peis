package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 通知类型：0.预约人员 1.报告领取通知(职业) 2.报告领取通知（健康） 3.会员生日提醒 6.核酸采样短信
 */
public enum SmsNotifyType {

    /**
     * 预约人员
     */
    YYRY(0),

    /**
     * 报告领取通知(职业)
     */
    BGLQUTZ_ZY(1),

    /**
     * 报告领取通知（健康）
     */
    BGLQTZ_JK(2),

    /**
     * 会员生日提醒
     */
    HYSRTX(3),

    /**
     * 核酸采样短信
     */
    HSCYDX(6);

    private Integer num;

    public Integer value() {
        return num;
    }

    SmsNotifyType(Integer num) {
        this.num = num;
    }

    public static SmsNotifyType instance(Integer value) {
        SmsNotifyType[] enums = values();
        for (SmsNotifyType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

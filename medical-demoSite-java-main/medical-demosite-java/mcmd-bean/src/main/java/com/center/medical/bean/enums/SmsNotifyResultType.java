package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 通知状态：0.通知失败 1.已取消 2.等待通知 3.已通知
 */
public enum SmsNotifyResultType {

    /**
     * 通知失败
     */
    TZSB(0),

    /**
     * 已取消
     */
    YQX(1),

    /**
     * 等待通知
     */
    DDTZ(2),

    /**
     * 已通知
     */
    YTZ(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    SmsNotifyResultType(Integer num) {
        this.num = num;
    }

    public static SmsNotifyResultType instance(Integer value) {
        SmsNotifyResultType[] enums = values();
        for (SmsNotifyResultType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

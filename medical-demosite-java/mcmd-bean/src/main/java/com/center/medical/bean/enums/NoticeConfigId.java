package com.center.medical.bean.enums;

/**
 * @author: AY
 * @date: 2025/3/3 19:11
 * @description: 通知配置类型id
 * 1 = OSS欠费通知
 */
public enum NoticeConfigId {

    /**
     * oss欠费通知
     */
    OSS_ARREARS("1"),

    /**
     * 短信欠费通知
     */
    SMS_OVERDUE("2"),

    /**
     * 订单通过通知
     */
    ORDER_APPROVED("3"),

    /**
     * 订单驳回通知
     */
    ORDER_REJECTION("4"),

    /**
     * 订单审核通知
     */
    ORDER_REVIEW("5"),

    /**
     * 报告总检通知
     */
    REPORT_TOTAL_INSPECTION("6"),

    /**
     * 报告未出通知
     */
    REPORT_NOT_RELEASED("7"),

    /**
     * 报告未出超时通知
     */
    REPORT_NOT_RELEASED_TIMEOUT("8");

    private String num;

    public String value() {
        return num;
    }

    NoticeConfigId(String num) {
        this.num = num;
    }

    public static NoticeConfigId instance(String value) {
        NoticeConfigId[] enums = values();
        for (NoticeConfigId statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

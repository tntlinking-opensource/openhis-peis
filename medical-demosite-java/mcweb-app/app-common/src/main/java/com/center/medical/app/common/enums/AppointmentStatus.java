package com.center.medical.app.common.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 预约状态：-1预约失败 1.待预约 2.已预约 3.申请改期中 4.改期成功 5.申请改期被驳回 6.预约结束 7.取消提交中 8.已取消
 */
public enum AppointmentStatus {

    /**
     * 预约失败
     */
    FAIL(-1, "预约失败"),

    /**
     * 待预约
     */
    PENDING(1, "待预约"),

    /**
     * 已预约
     */
    SUCCESS(2, "已预约"),

    /**
     * 申请改期中
     */
    CHANGEDATE_ING(3, "申请改期中"),

    /**
     * 改期成功
     */
    CHANGEDATE_ED(4, "改期成功"),

    /**
     * 申请改期被驳回
     */
    CHANGEDATE_FAIL(5, "申请改期被驳回"),

    /**
     * 预约结束
     */
    FINISHED(6, "预约结束"),

    /**
     * 取消提交中
     */
    CANCELING(7, "取消提交中"),

    /**
     * 已取消
     */
    CANCELED(8, "已取消");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    AppointmentStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static AppointmentStatus instance(Integer value) {
        AppointmentStatus[] enums = values();
        for (AppointmentStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        AppointmentStatus[] enums = values();
        for (AppointmentStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

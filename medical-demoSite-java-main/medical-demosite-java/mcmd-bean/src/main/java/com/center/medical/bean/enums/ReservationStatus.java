package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 预约状态：-1.预约失败 1.待预约 2.已预约 3.预约结束
 */
public enum ReservationStatus {

    /**
     * 预约失败
     */
    fail(-1, "预约失败"),

    /**
     * 待预约
     */
    PENDING(1, "待预约"),

    /**
     * 已预约
     */
    SUCCESS(2, "已预约"),

    /**
     * 预约结束
     */
    FINISHED(3, "预约结束");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    ReservationStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static ReservationStatus instance(Integer value) {
        ReservationStatus[] enums = values();
        for (ReservationStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        ReservationStatus[] enums = values();
        for (ReservationStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

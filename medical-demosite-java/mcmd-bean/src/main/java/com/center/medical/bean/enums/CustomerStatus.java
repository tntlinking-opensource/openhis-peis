package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 客户状态：0潜在 1正式 2释放
 */
public enum CustomerStatus {

    /**
     * 潜在
     */
    QIANZAI(0, "潜在"),

    /**
     * 正式
     */
    ZHENGSHI(1, "正式"),

    /**
     * 释放
     */
    SHIFANG(2, "释放");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    CustomerStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static CustomerStatus instance(Integer value) {
        CustomerStatus[] enums = values();
        for (CustomerStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        CustomerStatus[] enums = values();
        for (CustomerStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

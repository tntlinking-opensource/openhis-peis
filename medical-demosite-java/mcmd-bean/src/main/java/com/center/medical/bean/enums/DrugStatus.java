package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 药物状态 0.待处理 1.成交 3.退药 4.未成交
 */
public enum DrugStatus {

    /**
     * 待处理
     */
    DCL(0, "待处理"),

    /**
     * 成交
     */
    CJ(1, "成交"),

    /**
     * 退药
     */
    TY(3, "退药"),

    /**
     * 未成交
     */
    WCJ(4, "未成交");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    DrugStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static DrugStatus instance(Integer value) {
        DrugStatus[] enums = values();
        for (DrugStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        DrugStatus[] enums = values();
        for (DrugStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

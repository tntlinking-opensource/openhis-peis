package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 药物状态：1.高 2.中 3.低
 */
public enum Wjzjb {

    /**
     * 高
     */
    HEIGHT(1, "高"),

    /**
     * 中
     */
    MIDDLE(2, "中"),

    /**
     * 低
     */
    LOW(3, "低");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    Wjzjb(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static Wjzjb instance(Integer value) {
        Wjzjb[] enums = values();
        for (Wjzjb statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        Wjzjb[] enums = values();
        for (Wjzjb statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

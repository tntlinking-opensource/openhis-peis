package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 适用性别：0.男 1.女 2.通用
 */
public enum SexType {

    /**
     * 男
     */
    MALE(0, "男"),

    /**
     * 女
     */
    FEMALE(1, "女"),

    /**
     * 通用
     */
    COMMON(2, "通用");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    SexType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static SexType instance(Integer value) {
        SexType[] enums = values();
        for (SexType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        SexType[] enums = values();
        for (SexType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

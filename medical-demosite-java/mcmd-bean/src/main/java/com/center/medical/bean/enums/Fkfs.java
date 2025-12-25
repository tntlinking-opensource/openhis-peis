package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 付款方式：0.统收 1.自费
 */
public enum Fkfs {

    /**
     * 统收
     */
    TONGSHOU(0, "统收"),

    /**
     * 自费
     */
    ZIFEI(1, "自费");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    Fkfs(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static Fkfs instance(Integer value) {
        Fkfs[] enums = values();
        for (Fkfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        Fkfs[] enums = values();
        for (Fkfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

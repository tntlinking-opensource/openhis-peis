package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 沟通方式：0.电话 1.QQ 2.面对面 3.其它
 */
public enum Bcgtfs {

    /**
     * 电话
     */
    TEL(0, "电话"),

    /**
     * QQ
     */
    QQ(1, "QQ"),

    /**
     * 面对面
     */
    FACE(2, "面对面"),

    /**
     * 其它
     */
    OTHER(3, "其它");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    Bcgtfs(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static Bcgtfs instance(Integer value) {
        Bcgtfs[] enums = values();
        for (Bcgtfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        Bcgtfs[] enums = values();
        for (Bcgtfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

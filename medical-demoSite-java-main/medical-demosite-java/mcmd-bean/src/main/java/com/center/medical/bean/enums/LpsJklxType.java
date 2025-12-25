package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 接口类型：HIS、LIS、DR、US、CR、CT、EYE、MR TODO 确定这些名词的意义解释
 */
public enum LpsJklxType {

    /**
     * HIS
     */
    HIS("HIS"),

    /**
     * LIS
     */
    LIS("LIS"),

    /**
     * DR
     */
    DR("DR"),

    /**
     * US
     */
    US("US"),

    /**
     * CR
     */
    CR("CR"),

    /**
     * CT
     */
    CT("CT"),

    /**
     * EYE
     */
    EYE("EYE"),

    /**
     * MR
     */
    MR("MR");

    private String name;

    public String value() {
        return name;
    }

    LpsJklxType(String name) {
        this.name = name;
    }

    public static LpsJklxType instance(Integer value) {
        LpsJklxType[] enums = values();
        for (LpsJklxType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

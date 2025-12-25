package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 科室类型：0.常规 1.PACS科室 2.Lis科室
 */
public enum ksType {

    /**
     * 常规
     */
    NORMAL(0, "常规"),

    /**
     * PACS科室
     */
    PACS(1, "PACS科室"),

    /**
     * Lis科室
     */
    LIS(2, "Lis科室");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    ksType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static ksType instance(Integer value) {
        ksType[] enums = values();
        for (ksType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        ksType[] enums = values();
        for (ksType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

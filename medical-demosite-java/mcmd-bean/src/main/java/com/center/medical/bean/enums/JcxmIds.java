package com.center.medical.bean.enums;

/**
 * 检查项目ids
 */
public enum JcxmIds {

    SSYID("434"),


    SXYID("435"),


    MBID("15"),


    SGID("12"),

    TZID("13"),

    HXPLID("10000"),

    YBZKID("1163"),

    TEMPERATURE_EXAM_ID("402848e3700dfffe01704675865e0dfe"),

    /**
     * 血压结论id
     */
    XYJLID("436"),

    /**
     * 体重指数id
     */
    TZZSID("14"),

    ;

    private String num;

    public String value() {
        return num;
    }

    JcxmIds(String num) {
        this.num = num;
    }

    public static JcxmIds instance(Integer value) {
        JcxmIds[] enums = values();
        for (JcxmIds statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }



}

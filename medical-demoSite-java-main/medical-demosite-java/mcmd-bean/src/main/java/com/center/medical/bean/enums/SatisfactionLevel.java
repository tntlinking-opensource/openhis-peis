package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 星级 5非常满意   4满意  23基本满意  1不满意    不能评0
 */
public enum SatisfactionLevel {

    /**
     * 不能评0
     */
    ZERO(0),

    /**
     * 1不满意
     */
    ONE(1),

    /**
     * 23基本满意
     */
    TWO(2),

    /**
     * 23基本满意
     */
    THREE(3),

    /**
     * 4满意
     */
    FORU(4),

    /**
     * 5非常满意
     */
    FIVE(5);

    private Integer num;

    public Integer value() {
        return num;
    }

    SatisfactionLevel(Integer num) {
        this.num = num;
    }

    public static SatisfactionLevel instance(Integer value) {
        SatisfactionLevel[] enums = values();
        for (SatisfactionLevel statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 评价结果 1非常满意 2满意 3 基本满意 4 不满意 0不评价 无 未评价(整个分中心)//1非常满意 2满意4 不满意5取消不满意(科室)
 */
public enum AppraiseResult {

    /**
     * 未评价
     */
    ZERO(0),

    /**
     * 非常满意
     */
    ONE(1),

    /**
     * 满意
     */
    TWO(2),

    /**
     * 基本满意
     */
    THREE(3),

    /**
     * 不满意
     */
    FOUR(4);

    private Integer num;

    public Integer value() {
        return num;
    }

    AppraiseResult(Integer num) {
        this.num = num;
    }

    public static AppraiseResult instance(Integer value) {
        AppraiseResult[] enums = values();
        for (AppraiseResult statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

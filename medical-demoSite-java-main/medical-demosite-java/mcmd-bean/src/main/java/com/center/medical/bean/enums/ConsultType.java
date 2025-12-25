package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 咨询类型：1.现场咨询 2.来电咨询 3.电话回访
 */
public enum ConsultType {

    /**
     * 现场咨询
     */
    XCZX(1),

    /**
     * 来电咨询
     */
    LDZX(2),

    /**
     * 电话回访
     */
    DHHF(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    ConsultType(Integer num) {
        this.num = num;
    }

    public static ConsultType instance(Integer value) {
        ConsultType[] enums = values();
        for (ConsultType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

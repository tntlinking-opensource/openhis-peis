package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 客户证件类型：1.身份证 2.护照 6.军人证  7.港澳通行证/回乡证或台胞证
 */
public enum CusCardType {

    /**
     * 身份证
     */
    ID_CARD(1),

    /**
     * 护照
     */
    PASSPORT(2),

    /**
     * 军人证
     */
    MILITARY_CARD(6),

    /**
     * 港澳通行证/回乡证或台胞证
     */
    GANG_AO_TAI(7);

    private Integer num;

    public Integer value() {
        return num;
    }

    CusCardType(Integer num) {
        this.num = num;
    }

    public static CusCardType instance(Integer value) {
        CusCardType[] enums = values();
        for (CusCardType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

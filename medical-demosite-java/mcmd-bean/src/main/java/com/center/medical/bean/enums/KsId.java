package com.center.medical.bean.enums;

/**
 * 科室id
 */
public enum KsId {


    /**
     * 检验科id
     */
    JYKID(19);



    private Integer num;

    public Integer value() {
        return num;
    }

    KsId(Integer num) {
        this.num = num;
    }

    public static KsId instance(Integer value) {
        KsId[] enums = values();
        for (KsId statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}

package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
 */
public enum ZYTJLB {

    /**
     * 上岗前
     */
    SGQ(0),

    /**
     * 在岗期间
     */
    ZGQJ(1),

    /**
     * 离岗时
     */
    LGS(2),

    /**
     * 离岗后
     */
    LGH(3),

    /**
     * 应急
     */
    YJ(4);

    private Integer num;

    public Integer value() {
        return num;
    }

    ZYTJLB(Integer num) {
        this.num = num;
    }

    public static ZYTJLB instance(Integer value) {
        ZYTJLB[] enums = values();
        for (ZYTJLB statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

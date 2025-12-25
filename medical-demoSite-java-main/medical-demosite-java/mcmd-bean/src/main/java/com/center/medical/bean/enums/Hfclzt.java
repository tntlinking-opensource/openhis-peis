package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 回访处理状态：0.已提报 1.继续提报 2.已完成 3.已取消
 */
public enum Hfclzt {

    /**
     * 已提报
     */
    YITIBAO(0),

    /**
     * 继续提报
     */
    JIXUTIBAO(1),

    /**
     * 已完成
     */
    YIWANCHENG(2),

    /**
     * 已取消
     */
    YIQUXIAO(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    Hfclzt(Integer num) {
        this.num = num;
    }

    public static Hfclzt instance(Integer value) {
        Hfclzt[] enums = values();
        for (Hfclzt statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

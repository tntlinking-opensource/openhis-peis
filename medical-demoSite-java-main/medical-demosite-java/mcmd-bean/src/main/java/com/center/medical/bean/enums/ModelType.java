package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 模板类型：0：科室(个检)、1：团检、2：对比、3：单科室头模板
 */
public enum ModelType {

    /**
     * 科室(个检)
     */
    KS(0),

    /**
     * 团检
     */
    TJ(1),

    /**
     * 对比
     */
    DB(2),

    /**
     * 单科室头模板
     */
    DKSTMB(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    ModelType(Integer num) {
        this.num = num;
    }

    public static ModelType instance(Integer value) {
        ModelType[] enums = values();
        for (ModelType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

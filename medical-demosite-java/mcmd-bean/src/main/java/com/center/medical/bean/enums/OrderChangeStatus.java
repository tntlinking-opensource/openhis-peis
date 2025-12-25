package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更
 */
public enum OrderChangeStatus {

    /**
     * 审核未通过
     */
    SHWTG(0),

    /**
     * 已变更
     */
    YBG(2),

    /**
     * 变更已提交
     */
    BGYTJ(3),

    /**
     * 变更审核通过
     */
    BGSHTG(4),

    /**
     * 未变更
     */
    WBG(5);

    private Integer num;

    public Integer value() {
        return num;
    }

    OrderChangeStatus(Integer num) {
        this.num = num;
    }

    public static OrderChangeStatus instance(Integer value) {
        OrderChangeStatus[] enums = values();
        for (OrderChangeStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

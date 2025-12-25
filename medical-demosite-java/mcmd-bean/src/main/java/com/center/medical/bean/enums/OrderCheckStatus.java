package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 订单审批状态：0.审核未通过  1.已撤回 2.草稿 3.已提交 4.审核通过
 */
public enum OrderCheckStatus {

    /**
     * 审核未通过
     */
    SHBTG(0),

    /**
     * 已撤回
     */
    YCH(1),

    /**
     * 草稿
     */
    CG(2),

    /**
     * 已提交
     */
    YTJ(3),

    /**
     * 审核通过
     */
    SHTG(4);

    private Integer num;

    public Integer value() {
        return num;
    }

    OrderCheckStatus(Integer num) {
        this.num = num;
    }

    public static OrderCheckStatus instance(Integer value) {
        OrderCheckStatus[] enums = values();
        for (OrderCheckStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

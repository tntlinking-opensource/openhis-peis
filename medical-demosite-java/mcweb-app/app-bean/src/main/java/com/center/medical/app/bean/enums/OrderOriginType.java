package com.center.medical.app.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 订单生成来源 1:购买套餐
 */
public enum OrderOriginType {

    /**
     * 购买套餐
     */
    MEAL(1);

    private Integer num;

    public Integer value() {
        return num;
    }

    OrderOriginType(Integer num) {
        this.num = num;
    }

    public static OrderOriginType instance(Integer value) {
        OrderOriginType[] enums = values();
        for (OrderOriginType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

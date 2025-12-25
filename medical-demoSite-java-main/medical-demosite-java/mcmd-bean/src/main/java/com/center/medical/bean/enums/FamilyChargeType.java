package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 家庭卡收费类型：0.充值 1.消费 2.充值
 */
public enum FamilyChargeType {

    /**
     * 充值
     */
    RECHARGE(0, "充值"),

    /**
     * 消费
     */
    CONSUME(1, "消费"),

    /**
     * 充值
     */
    CHONGZHI(2, "充值");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    FamilyChargeType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static FamilyChargeType instance(Integer value) {
        FamilyChargeType[] enums = values();
        for (FamilyChargeType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        FamilyChargeType[] enums = values();
        for (FamilyChargeType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

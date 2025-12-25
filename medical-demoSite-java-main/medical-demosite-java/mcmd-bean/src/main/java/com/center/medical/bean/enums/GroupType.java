package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 备选组类型 0.组内选 1.组间选 2.组任选
 */
public enum GroupType {

    /**
     * 组内选
     */
    ZNX(0, "组内选"),

    /**
     * 组间选
     */
    ZJX(1, "组间选"),

    /**
     * 组任选
     */
    ZRX(2, "组任选");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    GroupType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static GroupType instance(Integer value) {
        GroupType[] enums = values();
        for (GroupType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        GroupType[] enums = values();
        for (GroupType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

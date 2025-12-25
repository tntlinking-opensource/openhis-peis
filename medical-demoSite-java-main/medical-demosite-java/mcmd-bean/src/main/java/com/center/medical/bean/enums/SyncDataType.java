package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 同步数据类型: 1.检查项目类型 2.检查项目 3.收费项目类型 4.收费项目
 */
public enum SyncDataType {

    /**
     * 检查项目类型
     */
    BASE_EXAM_ITEM_TYPE(1, "检查项目类型"),

    /**
     * 检查项目
     */
    BASE_EXAM_ITEM(2, "检查项目"),

    /**
     * 收费项目类型
     */
    ITEMS_TYPE(3, "收费项目类型"),

    /**
     * 收费项目
     */
    ITEMS(4, "收费项目");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    SyncDataType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static SyncDataType instance(Integer value) {
        SyncDataType[] enums = values();
        for (SyncDataType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        SyncDataType[] enums = values();
        for (SyncDataType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

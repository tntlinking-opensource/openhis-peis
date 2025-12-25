package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 同步数据操作类型: 1.新增 2.更新 3.删除
 */
public enum SyncOptType {

    /**
     * 查询
     */
    SELECT(1, "查询"),

    /**
     * 新增
     */
    ADD(2, "新增"),

    /**
     * 更新
     */
    UPDATE(3, "更新"),

    /**
     * 删除
     */
    DELETE(4, "删除");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    SyncOptType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static SyncOptType instance(Integer value) {
        SyncOptType[] enums = values();
        for (SyncOptType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        SyncOptType[] enums = values();
        for (SyncOptType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

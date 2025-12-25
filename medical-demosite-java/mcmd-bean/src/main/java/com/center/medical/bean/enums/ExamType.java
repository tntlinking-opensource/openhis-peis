package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 体检类型：0.健康体检 1.职业体检 2.综合 3.复查
 */
public enum ExamType {

    /**
     * 健康体检
     */
    JIANKANG(0, "健康"),

    /**
     * 职业体检
     */
    ZHIYE(1, "职业"),

    /**
     * 综合
     */
    ZONGHE(2, "综合"),

    /**
     * 复查
     */
    FUCHA(3, "复查");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    public String gName() {
        return name;
    }

    ExamType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static ExamType instance(Integer value) {
        ExamType[] enums = values();
        for (ExamType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        ExamType[] enums = values();
        for (ExamType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }


    public static Integer getValueByName(String name) {
        ExamType[] enums = values();
        System.out.println("类型列表：" + JSONUtil.toJsonStr(enums));
        for (ExamType enmu : enums) {
            if (enmu.gName().equals(name)) {
                return enmu.value();
            }
        }
        return null;
    }

}

package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
 */
public enum MedicalType {

    /**
     * 上岗前
     */
    SGQ(0, "上岗前"),

    /**
     * 在岗期间
     */
    ZGQJ(1, "在岗期间"),

    /**
     * 离岗时
     */
    LGS(2, "离岗时"),

    /**
     * 离岗后
     */
    LGH(3, "离岗后"),

    /**
     * 应急
     */
    YJ(4, "应急");

    private Integer num;

    private String name;

    public String gName() {
        return name;
    }

    public static String getName(Integer value) {
        MedicalType[] enums = values();
        for (MedicalType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }


    public static Integer getValueByName(String name) {
        MedicalType[] enums = values();
        System.out.println("类型列表：" + JSONUtil.toJsonStr(enums));
        for (MedicalType enmu : enums) {
            if (enmu.gName().equals(name)) {
                return enmu.value();
            }
        }
        return null;
    }

    public Integer value() {
        return num;
    }

    MedicalType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static MedicalType instance(Integer value) {
        MedicalType[] enums = values();
        for (MedicalType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

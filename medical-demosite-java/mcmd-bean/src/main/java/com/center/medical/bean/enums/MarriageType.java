package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他
 */
public enum MarriageType {

    /**
     * 未婚
     */
    WH(1, "未婚"),

    /**
     * 已婚
     */
    YH(2, "已婚"),

    /**
     * 离异
     */
    LY(3, "离异"),

    /**
     * 丧偶
     */
    SO(4, "丧偶"),

    /**
     * 其他
     */
    QT(5, "其他");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    public String gName() {
        return name;
    }

    MarriageType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static MarriageType instance(Integer value) {
        MarriageType[] enums = values();
        for (MarriageType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        MarriageType[] enums = values();
        for (MarriageType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static Integer getValueByName(String name) {
        MarriageType[] enums = values();
        System.out.println("婚姻类型列表：" + JSONUtil.toJsonStr(enums));
        for (MarriageType statusEnum : enums) {
            if (statusEnum.gName().equals(name)) {
                return statusEnum.value();
            }
        }
        return null;
    }


    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        MarriageType[] enums = values();
        for (int i = 0; i < enums.length; i++) {
            map.put(enums[i].value().toString(), enums[i].gName());
        }
        return map;
    }

}

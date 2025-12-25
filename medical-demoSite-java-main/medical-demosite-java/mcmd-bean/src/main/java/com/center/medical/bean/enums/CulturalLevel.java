package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 文化水平：0.小学 1.初中 2.技校 3.职高 4.高中 5.中专 6.大专 7.大学 8.研究生以上
 */
public enum CulturalLevel {

    /**
     * 小学
     */
    xiaoxue(0, "小学"),

    /**
     * 初中
     */
    chuzhong(1, "初中"),

    /**
     * 技校
     */
    jixiao(2, "技校"),

    /**
     * 职高
     */
    zhigao(3, "职高"),

    /**
     * 高中
     */
    gaozhong(4, "高中"),

    /**
     * 中专
     */
    zhongzhuan(5, "中专"),

    /**
     * 大专
     */
    dazhuan(6, "大专"),

    /**
     * 大学
     */
    daxue(7, "大学"),

    /**
     * 研究生以上
     */
    yanjiusheng(8, "研究生以上");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    public String gName() {
        return name;
    }

    public static String getName(Integer value) {
        CulturalLevel[] enums = values();
        for (CulturalLevel statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }


    public static Integer getValueByName(String name) {
        CulturalLevel[] enums = values();
        System.out.println("类型列表：" + JSONUtil.toJsonStr(enums));
        for (CulturalLevel enmu : enums) {
            if (enmu.gName().equals(name)) {
                return enmu.value();
            }
        }
        return null;
    }

    CulturalLevel(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static CulturalLevel instance(Integer value) {
        CulturalLevel[] enums = values();
        for (CulturalLevel statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

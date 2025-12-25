package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 机构类型 1.专业机构 2.公立三甲 3.公立医院 4.民营医院 5.养老中心
 */
public enum BranchType {

    /**
     * 专业机构
     */
    ZYJG(1, "专业机构"),

    /**
     * 公立三甲
     */
    GLSJ(2, "公立三甲"),

    /**
     * 公立医院
     */
    GLYY(3, "公立医院"),

    /**
     * 民营医院
     */
    MYYY(4, "民营医院"),

    /**
     * 养老中心
     */
    YLZX(5, "养老中心");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    BranchType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static BranchType instance(Integer value) {
        BranchType[] enums = values();
        for (BranchType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        BranchType[] enums = values();
        for (BranchType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

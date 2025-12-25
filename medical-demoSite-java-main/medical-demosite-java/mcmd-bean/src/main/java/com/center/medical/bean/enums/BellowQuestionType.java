package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 检查类型：1.溶血 2.脂血 3.凝血 4.多采集 5.容器不符 6.标本量错误 7.标识不清 8.TCL
 */
public enum BellowQuestionType {

    /**
     * 溶血
     */
    RX(1, "溶血"),

    /**
     * 脂血
     */
    ZX(2, "脂血"),

    /**
     * 凝血
     */
    NX(3, "凝血"),

    /**
     * 多采集
     */
    DCJ(4, "多采集"),

    /**
     * 容器不符
     */
    RQBF(5, "容器不符"),

    /**
     * 标本量错误
     */
    BBLCW(6, "标本量错误"),

    /**
     * 标识不清
     */
    BSBQ(7, "标识不清"),

    /**
     * TCL
     */
    TCL(8, "TCL");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    BellowQuestionType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static BellowQuestionType instance(Integer value) {
        BellowQuestionType[] enums = values();
        for (BellowQuestionType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        BellowQuestionType[] enums = values();
        for (BellowQuestionType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

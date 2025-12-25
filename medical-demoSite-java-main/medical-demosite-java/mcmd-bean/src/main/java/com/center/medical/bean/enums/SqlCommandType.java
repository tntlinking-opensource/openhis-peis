package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: SQL语句类型：1.SELECT 2.INSERT 3.UPDATE 4.DELETE 5.FLUSH 6.UNKNOWN
 */
public enum SqlCommandType {

    /**
     * 表示SQL语句是一个查询语句
     */
    SELECT(1, "SELECT"),

    /**
     * 表示SQL语句是一个插入语句
     */
    INSERT(2, "INSERT"),

    /**
     * 表示SQL语句是一个更新语句
     */
    UPDATE(3, "UPDATE"),

    /**
     * 表示SQL语句是一个删除语句
     */
    DELETE(4, "DELETE"),

    /**
     * 表示SQL语句是一个刷新语句
     */
    FLUSH(5, "FLUSH"),

    /**
     * 表示SQL语句的类型未知或无法确定
     */
    UNKNOWN(6, "UNKNOWN");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    SqlCommandType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static SqlCommandType instance(Integer value) {
        SqlCommandType[] enums = values();
        for (SqlCommandType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        SqlCommandType[] enums = values();
        for (SqlCommandType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

    public static int getNum(String name) {
        SqlCommandType[] enums = values();
        for (SqlCommandType statusEnum : enums) {
            if (statusEnum.name().equals(name)) {
                return statusEnum.num;
            }
        }
        return -1;
    }

}

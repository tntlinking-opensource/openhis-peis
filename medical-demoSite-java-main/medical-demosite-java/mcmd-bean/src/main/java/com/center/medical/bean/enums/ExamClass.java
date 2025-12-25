package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 检查类型：0.健康类 1.职业类 2.综合类 5.入职类 6.疫苗类 7.其他类
 */
public enum ExamClass {

    /**
     * 健康类
     */
    JIANKANG(0),

    /**
     * 职业类
     */
    ZHIYE(1),

    /**
     * 综合类
     */
    ZONGHE(2),

    /**
     * 入职类
     */
    RUZHI(5),

    /**
     * 疫苗类
     */
    YIMIAO(6),

    /**
     * 其他类
     */
    OTHER(7);

    private Integer num;

    public Integer value() {
        return num;
    }

    ExamClass(Integer num) {
        this.num = num;
    }

    public static ExamClass instance(Integer value) {
        ExamClass[] enums = values();
        for (ExamClass statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 加项处理类型：0.加项(含检验科普通增加) 1.弃检 2.补检 3.迟检（检验科） 4.退项（检验科）
 */
public enum NewProjectHandleType {

    /**
     * 加项(含检验科普通增加)
     */
    JX(0),

    /**
     * 弃检
     */
    QJ(1),

    /**
     * 补检
     */
    BJ(2),

    /**
     * 迟检（检验科）
     */
    CJ(3),

    /**
     * 退项（检验科）
     */
    TX(4);

    private Integer num;

    public Integer value() {
        return num;
    }

    NewProjectHandleType(Integer num) {
        this.num = num;
    }

    public static NewProjectHandleType instance(Integer value) {
        NewProjectHandleType[] enums = values();
        for (NewProjectHandleType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

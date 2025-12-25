package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 客户跟踪阶段 0.需求交流 1.方案谈判 2.价格谈判 3.体检确认 4.合同签订
 */
public enum CustomerFollowStatus {

    /**
     * 需求交流
     */
    ZYJG(0, "需求交流"),

    /**
     * 方案谈判
     */
    GLSJ(1, "方案谈判"),

    /**
     * 价格谈判
     */
    GLYY(2, "价格谈判"),

    /**
     * 体检确认
     */
    MYYY(3, "体检确认"),

    /**
     * 合同签订
     */
    YLZX(4, "合同签订");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    CustomerFollowStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static CustomerFollowStatus instance(Integer value) {
        CustomerFollowStatus[] enums = values();
        for (CustomerFollowStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        CustomerFollowStatus[] enums = values();
        for (CustomerFollowStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

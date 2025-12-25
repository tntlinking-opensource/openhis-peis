package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 济南市职业卫生综合管理平台上传状态：-1未录入必填字段不能上传 0待上传 1已上传  2上传失败
 */
public enum JinanStatus {

    /**
     * 未录入必填字段不能上传
     */
    UNABLE(-1, "未录入必填字段不能上传"),

    /**
     * 0待上传
     */
    PENDING(0, "待上传"),

    /**
     * 已上传
     */
    UPLOADED(1, "已上传"),

    /**
     * 上传失败
     */
    FAIL(2, "上传失败");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    JinanStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static JinanStatus instance(Integer value) {
        JinanStatus[] enums = values();
        for (JinanStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        JinanStatus[] enums = values();
        for (JinanStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

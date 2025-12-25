package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 回访处理方式：0.当面告知 1.电话通知 2.短信通知
 */
public enum Zjclfs {

    /**
     * 健康体检
     */
    JIANKANG(0),

    /**
     * 职业体检
     */
    ZHIYE(1),

    /**
     * 综合
     */
    ZONGHE(2),

    /**
     * 复查
     */
    FUCHA(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    Zjclfs(Integer num) {
        this.num = num;
    }

    public static Zjclfs instance(Integer value) {
        Zjclfs[] enums = values();
        for (Zjclfs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

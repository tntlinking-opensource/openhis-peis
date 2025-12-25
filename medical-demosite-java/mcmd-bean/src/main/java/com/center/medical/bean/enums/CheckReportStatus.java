package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 报告状态：0.草稿 1.提交 2.通过 3.不通过 4.撤回 5.主检未审 6.主检开审 7.主检已审
 */
public enum CheckReportStatus {

    /**
     * 草稿
     */
    DRAFT(0),

    /**
     * 提交
     */
    SUBMIT(1),

    /**
     * 通过
     */
    PASSED(2),

    /**
     * 不通过
     */
    FAILED(3),

    /**
     * 撤回
     */
    RETRACT(4),

    /**
     * 主检未审
     */
    UN_VERIFY(5),

    /**
     * 主检开审
     */
    VERIFYING(6),

    /**
     * 主检已审
     */
    VERIFYED(7);

    private Integer num;

    public Integer value() {
        return num;
    }

    CheckReportStatus(Integer num) {
        this.num = num;
    }

    public static CheckReportStatus instance(Integer value) {
        CheckReportStatus[] enums = values();
        for (CheckReportStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

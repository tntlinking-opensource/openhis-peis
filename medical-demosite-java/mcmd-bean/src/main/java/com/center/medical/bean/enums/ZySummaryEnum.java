package com.center.medical.bean.enums;

/**
 * 职业健康检查结论
 * @author xhp
 * @since 2023-06-13 10:29
 */
public enum ZySummaryEnum {
    /**
     * 职业禁忌症
     */
    occupationalContraindication(2),
    /**
     * 疑似职业病
     */
    occupationalDiseases(1),
    /**
     * 复查
     */
    review(3),

    ;


    //md_zy_summary.serial_no
    private int serialNo;

    ZySummaryEnum(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getSerialNo() {
        return serialNo;
    }
}

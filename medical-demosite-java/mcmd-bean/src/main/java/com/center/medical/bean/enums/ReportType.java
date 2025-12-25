package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 报告类型：0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告
 */
public enum ReportType {

    /**
     * 检验报告
     */
    JYBG(0),

    /**
     * 老人查体-分析报告
     */
    LRCT(1),

    /**
     * 隐私报告
     */
    YSBG(2),

    /**
     * 临时报告
     */
    LSBG(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    ReportType(Integer num) {
        this.num = num;
    }

    public static ReportType instance(Integer value) {
        ReportType[] enums = values();
        for (ReportType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

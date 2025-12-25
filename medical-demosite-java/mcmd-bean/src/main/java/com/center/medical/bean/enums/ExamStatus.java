package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取
 */
public enum ExamStatus {

    /**
     * 总检开始
     */
    ZJKS(0),

    /**
     * 总检完成
     */
    ZJWC(1),

    /**
     * 报告已打印
     */
    BGYDY(2),

    /**
     * 报告一审通过
     */
    BGYSTG(3),

    /**
     * 报告一审不通过
     */
    BGYSBTG(4),

    /**
     * 二审通过
     */
    BGESTG(5),

    /**
     * 二审不通过
     */
    BGESBTG(6),

    /**
     * 终审通过
     */
    BGZSTG(7),

    /**
     * 终审不通过
     */
    BGZSBTG(8),

    /**
     * 报告已交接
     */
    BGYJJ(9),

    /**
     * 报告已通知
     */
    BGYTZ(10),

    /**
     * 报告已领取
     */
    BGYLQ(11);

    private Integer num;

    public Integer value() {
        return num;
    }

    ExamStatus(Integer num) {
        this.num = num;
    }

    public static ExamStatus instance(Integer value) {
        ExamStatus[] enums = values();
        for (ExamStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

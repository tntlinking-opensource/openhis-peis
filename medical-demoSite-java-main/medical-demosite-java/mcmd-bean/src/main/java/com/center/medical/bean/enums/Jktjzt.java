package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 总检状态：-2.检验报告,-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.
 * * 报告已交接、10.报告已通知、11.报告已领取
 */
public enum Jktjzt {

//    /**
//     * 检验报告
//     */
//    JYBG(-2),

    /**
     * 总检未开始
     */
    ZJWKS(-1),

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

    private Integer name;

    public Integer value() {
        return name;
    }

    Jktjzt(Integer name) {
        this.name = name;
    }

    public static Jktjzt instance(Integer value) {
        Jktjzt[] enums = values();
        for (Jktjzt statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

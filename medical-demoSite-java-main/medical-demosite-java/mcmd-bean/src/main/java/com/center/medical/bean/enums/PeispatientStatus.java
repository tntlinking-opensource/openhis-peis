package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 体检状态：0.待预约 1.待登记 2.待检查（已登记） 3.待检查（已登记） 4.检查中 5.待交单（检查完成） 6.待审核（已交单）
 * 7.待总检（审核完成） 8.待报告一审 9.待报告二审 10.待报告终审 11.待交接 12.待通知 13。待领取 14.已领取
 */
public enum PeispatientStatus {

    /**
     * 待预约
     */
    dyy(0, "待预约"),

    /**
     * 待登记
     */
    ddj(1, "待登记"),

    /**
     * 待登记
     */
    dzf(2, "待支付"),

    /**
     * 待检查
     */
    djc(3, "待检查"),

    /**
     * 检查中
     */
    jcz(4, "检查中"),

    /**
     * 待交单
     */
    djd(5, "待交单"),

    /**
     * 待审核
     */
    dsh(6, "待审核"),

    /**
     * 待总检
     */
    dzj(7, "待总检"),

    /**
     * 待报告一审
     */
    dys(8, "待报告一审"),

    /**
     * 待报告二审
     */
    des(9, "待报告二审"),

    /**
     * 待报告终审
     */
    dzs(10, "待报告终审"),

    /**
     * 待交接
     */
    djj(11, "待交接"),

    /**
     * 待通知
     */
    dtz(12, "待通知"),

    /**
     * 待领取
     */
    dlq(13, "待领取"),

    /**
     * 已领取
     */
    ylq(14, "已领取");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    PeispatientStatus(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static PeispatientStatus instance(Integer value) {
        PeispatientStatus[] enums = values();
        for (PeispatientStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        PeispatientStatus[] enums = values();
        for (PeispatientStatus statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }

}

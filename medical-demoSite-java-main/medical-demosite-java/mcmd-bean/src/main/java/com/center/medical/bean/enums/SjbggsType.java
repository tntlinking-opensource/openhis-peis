package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 数据报告格式：1.检验科检查、2.外送项目检查、3.一般检查、4.C13检查、5:肺功能检查、6.无图像检查、7.图像检查、8.问诊检查、9.电测听检查、10.骨密度检查、11.视力检查、12.健康检查
 */
public enum SjbggsType {

    /**
     * 检验科检查
     */
    JYK(1),

    /**
     * 外送项目检查
     */
    WSXM(2),

    /**
     * 一般检查
     */
    YB(3),

    /**
     * C13检查
     */
    C13(4),

    /**
     * 肺功能检查
     */
    FGN(5),

    /**
     * 无图像检查
     */
    WTX(6),

    /**
     * 图像检查
     */
    TX(7),

    /**
     * 问诊检查
     */
    WZ(8),

    /**
     * 电测听检查
     */
    DCT(9),

    /**
     * 骨密度检查
     */
    FMD(10),

    /**
     * 视力检查
     */
    SL(11),

    /**
     * 健康检查
     */
    JK(12);

    private Integer num;

    public Integer value() {
        return num;
    }

    SjbggsType(Integer num) {
        this.num = num;
    }

    public static SjbggsType instance(Integer value) {
        SjbggsType[] enums = values();
        for (SjbggsType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

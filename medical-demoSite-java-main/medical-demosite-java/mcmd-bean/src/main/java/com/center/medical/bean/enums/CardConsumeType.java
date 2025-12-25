package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品 6.团体结算
 */
public enum CardConsumeType {

    /**
     * 体检
     */
    TIJIAN(0),

    /**
     * 药房
     */
    YAOFANG(1),

    /**
     * 口腔科
     */
    KOUQIANGKE(2),

    /**
     * 眼镜店
     */
    YANJINGDIAN(3),

    /**
     * 合作
     */
    HEZUO(4),

    /**
     * 保健品
     */
    BAOJIANP(5),

    /**
     * 团体结算
     */
    TUANTI(6);

    private Integer num;

    public Integer value() {
        return num;
    }

    CardConsumeType(Integer num) {
        this.num = num;
    }

    public static CardConsumeType instance(Integer value) {
        CardConsumeType[] enums = values();
        for (CardConsumeType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

package com.center.medical.bean.enums;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 回访处理方式 [{id:'0',text:'当面告知'},{id:'1',text:'电话通知'},{id:'2',text:'短信通知'},{id:'3',text:'继续提报'}]
 */
public enum Hffffs {

    /**
     * 当面告知
     */
    DMGZ(0),

    /**
     * 电话通知
     */
    DHTZ(1),

    /**
     * 短信通知
     */
    DXTZ(2),

    /**
     * 继续提报
     */
    JXTB(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    Hffffs(Integer num) {
        this.num = num;
    }

    public static Hffffs instance(Integer value) {
        Hffffs[] enums = values();
        for (Hffffs statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}

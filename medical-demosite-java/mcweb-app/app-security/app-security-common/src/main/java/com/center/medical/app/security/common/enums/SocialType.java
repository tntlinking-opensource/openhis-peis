package com.center.medical.app.security.common.enums;

/**
 * 第三方系统类型
 *
 * @author FrozenWatermelon
 * @date 2021/01/16
 */
public enum SocialType {

    /**
     * 普通用户系统
     */
    ORDINARY(0),

    /**
     * 小程序
     */
    MA(1),

    /**
     * 公众号
     */
    MP(2),

    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    SocialType(Integer value) {
        this.value = value;
    }

}

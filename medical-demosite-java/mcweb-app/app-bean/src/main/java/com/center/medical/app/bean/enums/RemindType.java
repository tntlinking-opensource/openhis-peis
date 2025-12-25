/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.enums;


/**
 * 通知类型 1.短信发送 2.公众号订阅消息 3.站内消息
 *
 * @author Yami
 */
public enum RemindType {


    /**
     * 短信发送
     */
    SMS(1),

    /**
     * 公众号订阅消息
     */
    MP(2),

    /**
     * 站内消息
     */
    MINI(3);

    private Integer num;

    public Integer value() {
        return num;
    }

    RemindType(Integer num) {
        this.num = num;
    }

    public static RemindType instance(Integer value) {
        RemindType[] enums = values();
        for (RemindType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}

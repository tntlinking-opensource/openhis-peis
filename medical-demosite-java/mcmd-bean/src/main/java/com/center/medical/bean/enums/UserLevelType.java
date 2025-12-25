/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.bean.enums;

/**
 * 会员等级类型
 *
 * @author yami
 */
public enum UserLevelType {
    /**
     * 普通会员
     */
    ordinary(1),

    /**
     * vip会员
     */
    vip(2),

    /**
     * vvip会员
     */
    vvip(3),

    /**
     * 贵宾
     */
    guest(4),
    ;

    /**
     * md_user_level.level_id
     */
    private Integer num;

    public Integer value() {
        return num;
    }

    UserLevelType(Integer num) {
        this.num = num;
    }

    public static UserLevelType instance(Integer value) {
        UserLevelType[] enums = values();
        for (UserLevelType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}

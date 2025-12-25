package com.center.medical.center.qingdao.profession.entity.enumerate;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum SexType {
    UNKNOWN("0", "未知的性别"),
    MALE("1", "男性"),
    FEMALE("2", "女性"),
    NOT_STATED("9", "未说明的性别"),

    ;
    private final String value;
    private final String name;

    public static SexType get(String value) {
        return EnumUtil.likeValueOf(SexType.class, value);
    }

    public static String getName(String name) {
        return Optional.ofNullable(get(name)).map(SexType::getName).orElse(null);
    }

    public static String getValue(String value) {
        return Optional.ofNullable(get(value)).map(SexType::getValue).orElse(null);
    }
}
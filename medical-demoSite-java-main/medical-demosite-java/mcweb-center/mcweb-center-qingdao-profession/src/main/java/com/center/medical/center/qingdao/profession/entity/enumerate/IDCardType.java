package com.center.medical.center.qingdao.profession.entity.enumerate;

import cn.hutool.core.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum IDCardType {
    ID_CARD("01", "居民身份证", "1"),
    //    ACCOUNT_BOOK("02", "居民户口簿",null),
//    PASSPORT("03", "护照",null),
    OFFICER_ID("04", "军官证", "2"),
    //    DRIVER_LICENSE("05", "驾驶证",null),
    HONG_KONG_AND_MACAU_RESIDENTS("06", "港澳居民来往内地通行证", "6"),
    TAIWANESE_RESIDENTS("07", "台湾居民来往内地通行证", "7"),
    NOT_OBTAINED("88", "暂未获取", "8"),
    OTHER("99", "其他法定有效证件", "9"),

    ;
    private final String value;
    private final String name;
    private final String code;

    public static IDCardType get(String value) {
        return EnumUtil.likeValueOf(IDCardType.class, value);
    }

    public static String getName(String name) {
        return Optional.ofNullable(get(name)).map(IDCardType::getName).orElse(null);
    }

    public static String getValue(String value) {
        return Optional.ofNullable(get(value)).map(IDCardType::getValue).orElse(null);
    }
}
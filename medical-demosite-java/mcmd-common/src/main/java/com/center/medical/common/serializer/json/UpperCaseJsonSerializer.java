/*
 * Copyright (c) 2022-2999 青岛易筑科技有限公司 All rights reserved.
 *
 * https://www.jixieguanjia.com.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.serializer.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 转换成大写
 */
@Slf4j
@Component
public class UpperCaseJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        log.info("转换成大写:" + value);
        gen.writeString(value.toUpperCase());
    }
}

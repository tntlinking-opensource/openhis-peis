/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.serializer.json;

import cn.hutool.core.util.StrUtil;
import com.center.medical.app.common.bean.Domain;
import com.center.medical.app.common.bean.SysConfig;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.util.CacheManagerUtil;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.common.util.SpringContextUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片加上前缀
 *
 * @author yami
 */
@Component
public class ImgJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        CacheManagerUtil cacheManagerUtil = SpringContextUtils.getBean(CacheManagerUtil.class);
        if (StrUtil.isBlank(value)) {
            gen.writeString(StrUtil.EMPTY);
            return;
        }
        String[] imgs = value.split(StrUtil.COMMA);
        StringBuilder sb = new StringBuilder();
        String rule = "^((http[s]{0,1})://)";
        Pattern pattern = Pattern.compile(rule);

        Domain domain = cacheManagerUtil.getCache("SysConfigObject", Constant.DOMAIN_CONFIG);
        if (domain == null) {
            SysConfig config = SpringContextUtils.getBean(SqlSession.class).selectOne("com.center.medical.app.dao.SysConfigMapper.queryByKey", Constant.DOMAIN_CONFIG);
            domain = Json.parseObject(config.getParamValue(), Domain.class);
            cacheManagerUtil.putCache("SysConfigObject", Constant.DOMAIN_CONFIG, domain);
        }

        for (String img : imgs) {
            Matcher matcher = pattern.matcher(img);
            //若图片以http或https开头，直接返回
            if (matcher.find()) {
                sb.append(img).append(StrUtil.COMMA);
            } else {
                sb.append(domain.getMdResources()).append("/").append(img).append(StrUtil.COMMA);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        gen.writeString(sb.toString());
    }
}

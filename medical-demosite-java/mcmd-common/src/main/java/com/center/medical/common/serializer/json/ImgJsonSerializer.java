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

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.utils.CacheManagerUtil;
import com.center.medical.common.utils.SpringContextUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 图片加上前缀
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

        Domain domain = cacheManagerUtil.getCache("SysConfigObject", Constants.DOMAIN_CONFIG);
        if (domain == null) {
            SysConfig config = SpringContextUtils.getBean(SqlSession.class).selectOne("com.center.medical.system.dao.SysConfigMapper.checkConfigKeyUnique", Constants.DOMAIN_CONFIG);
            domain = JSONUtil.toBean(config.getConfigValue(), Domain.class);
            cacheManagerUtil.putCache("SysConfigObject", Constants.DOMAIN_CONFIG, domain);
        }

        for (String img : imgs) {
            Matcher matcher = pattern.matcher(img);
            //若图片以http或https开头，直接返回
            if (matcher.find()) {
                sb.append(img).append(StrUtil.COMMA);
            } else {
                if (ZhongkangConfig.isOnline()) {
                    sb.append(domain.getRsPfDomain()).append("/").append(img).append(StrUtil.COMMA);
                } else {
                    sb.append(domain.getRsLcDomain()).append("/").append(img).append(StrUtil.COMMA);
                }

            }
        }
        sb.deleteCharAt(sb.length() - 1);
        gen.writeString(sb.toString());
    }
}

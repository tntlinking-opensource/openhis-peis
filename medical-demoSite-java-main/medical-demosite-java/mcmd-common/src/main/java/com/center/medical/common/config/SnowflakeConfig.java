package com.center.medical.common.config;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 雪花算法订单号生成
 */
@Configuration
public class SnowflakeConfig {

    @Value("${application.datacenterId}")
    private Long datacenterId;

    @Value("${application.workerId}")
    private Long workerId;

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(workerId, datacenterId);
    }

}

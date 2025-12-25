/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.config;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 *
 * @author yami
 */
@Configuration
@AllArgsConstructor
public class ShopBeanConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}

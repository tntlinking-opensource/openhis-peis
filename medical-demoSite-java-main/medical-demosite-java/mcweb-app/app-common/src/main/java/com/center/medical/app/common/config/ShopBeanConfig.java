/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.config;

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

/*
 * Copyright (c) 2022-2999 青岛麦沃德网络科技有限公司 All rights reserved.
 *
 * https://www.myworld.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: MapperFacade 用于dto ->entity的转换
 */
@Configuration
public class OrikaConfig {

    @Bean
    public MapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    @Bean
    public MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}

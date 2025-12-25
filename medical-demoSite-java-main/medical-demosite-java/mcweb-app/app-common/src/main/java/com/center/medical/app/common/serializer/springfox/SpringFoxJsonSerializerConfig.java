/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.serializer.springfox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.util.List;

/**
 * @author LGH
 */
@Configuration
public class SpringFoxJsonSerializerConfig {

    @Bean
    @Primary
    public JsonSerializer yamiSpringfoxJsonSerializer(List<JacksonModuleRegistrar> moduleRegistrars) {
        return new SpringfoxJsonSerializer(moduleRegistrars);
    }
}

package com.center.medical.outside.config;

import com.center.medical.outside.handler.OpenApiAuthHandler;
import com.center.medical.outside.handler.OpenApiAuthV2Handler;
import com.center.medical.outside.handler.OpenApiAuthV3Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义业务配置
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:50
 */
@Configuration
public class OutsideWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private OpenApiAuthHandler openApiAuthHandler;
    @Autowired
    private OpenApiAuthV2Handler openApiAuthV2Handler;
    @Autowired
    private OpenApiAuthV3Handler openApiAuthV3Handler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(openApiAuthHandler).addPathPatterns("/open/api/v1/**"); //需要AES+RSA加密授权接口路径
        registry.addInterceptor(openApiAuthV2Handler).addPathPatterns("/open/api/v2/**"); //需要RSA加密授权接口路径
        registry.addInterceptor(openApiAuthV3Handler).addPathPatterns("/open/api/v3/**"); //需要RSA加密授权和token认证接口路径
        registry.addInterceptor(openApiAuthV3Handler).addPathPatterns("/open/api/v4/**"); //需要RSA加密授权和token认证接口路径
    }
}

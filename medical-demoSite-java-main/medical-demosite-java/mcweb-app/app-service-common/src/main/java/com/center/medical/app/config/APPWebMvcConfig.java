package com.center.medical.app.config;

import com.center.medical.app.handler.OpenApiHandler;
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
public class APPWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private OpenApiHandler openApiHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(openApiHandler).addPathPatterns("/open/api/v1/**");
    }
}

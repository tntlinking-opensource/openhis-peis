package com.center.medical.app.common.config;

import com.center.medical.app.common.i18n.YamiLocaleChangeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author LGH
 */
@Configuration
public class WebMvcConfig {

    @Autowired
    private YamiLocaleChangeInterceptor localeChangeInterceptor;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            //拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
            }
        };
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }
}

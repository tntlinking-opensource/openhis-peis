package com.center.medical.ai.admin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: 路飞
 * @date: 2025/03/4 15:57
 * @description: 沃德AI系统-web容器中进行部署
 */
public class AiServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AiApplication.class);
    }
}

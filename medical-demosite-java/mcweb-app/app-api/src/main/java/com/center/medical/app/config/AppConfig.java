package com.center.medical.app.config;

import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 路飞
 * @date: 2023-05-12 19:25
 * @description:
 */
@Configuration
public class AppConfig {
    @Bean
    public ShutdownEndpoint shutdownEndpoint() {
        return new ShutdownEndpoint();
    }
}

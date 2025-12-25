package com.center.medical.center.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 静态读取yml里面的数据
 */
@Component
public class PropertyUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static String getProperty(String key) {
        return context.getEnvironment().getProperty(key);
    }
}

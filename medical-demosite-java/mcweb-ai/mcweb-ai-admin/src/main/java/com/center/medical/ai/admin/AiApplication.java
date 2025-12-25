package com.center.medical.ai.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: 路飞
 * @date: 2025/03/4 15:57
 * @description: 沃德AI系统-启动程序
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.center.medical")
public class AiApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(AiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  沃德AI系统启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}

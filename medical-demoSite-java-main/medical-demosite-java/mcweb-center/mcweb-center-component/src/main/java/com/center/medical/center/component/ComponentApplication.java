package com.center.medical.center.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author 路飞
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.center.medical")
@EnableScheduling
public class ComponentApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ComponentApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  分中心组件系统启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}

package com.center.medical.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @author 路飞
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.center.medical")
public class AdminApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  沃德健管云平台启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}

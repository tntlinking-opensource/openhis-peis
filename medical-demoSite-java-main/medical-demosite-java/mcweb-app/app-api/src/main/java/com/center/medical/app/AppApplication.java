package com.center.medical.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @author 路飞
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.center.medical"})
public class AppApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");

        SpringApplication.run(AppApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  沃德APP启动成功   ლ(´ڡ`ლ)ﾞ  ");

    }
}

package com.center.medical.enterprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @author 路飞
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.center.medical.enterprise"})
public class EpsApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");

        SpringApplication.run(EpsApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  沃德企业门户网站启动成功   ლ(´ڡ`ლ)ﾞ  ");

    }
}

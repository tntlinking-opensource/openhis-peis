package com.center.medical.outreach.lc;

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
public class OutreachLcApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");

        SpringApplication.run(OutreachLcApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  沃德健管云平台内网外联系统启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}

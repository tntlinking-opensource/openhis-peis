package com.center.medical.center.qingdao.profession;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.center.medical.center.qingdao.profession.entity.properties.*;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@EnableSpringUtil
@EnableAsync
@EnableCaching
@EnableConfigurationProperties({ConfigProperties.class, ExcelProperties.class, QjkProperties.class, JzjkProperties.class, HnjkProperties.class, WfjkProperties.class})
@EnableKnife4j
@EnableSwagger2WebMvc
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@ComponentScan("com.center.medical")
public class QingdaoProfessionApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QingdaoProfessionApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  职业数据上传系统启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }

}

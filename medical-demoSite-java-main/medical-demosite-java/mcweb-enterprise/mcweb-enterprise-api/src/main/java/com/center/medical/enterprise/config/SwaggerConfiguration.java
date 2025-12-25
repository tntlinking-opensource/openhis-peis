/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 沃德健管云平台后端接口文档
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

    // 定义分隔符,配置Swagger多包
    private static final String splitor = ";";

    // 创建Docket存入容器，Docket代表一个接口文档
    @Bean("mainConfig")
    public Docket mainApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("沃德体检企业门户系统接口Main")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.enterprise.controller" + splitor + "com.center.medical.enterprise.security.controller" + splitor + "com.center.medical.enterprise.security.common.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    // 创建接口文档的具体信息，会显示在接口文档页面中
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("沃德体检企业门户系统接口api")
                // 文档描述
                .description("沃德体检企业门户系统接口api文档Swagger版")
                // 版本
                .version("V1.0.0")
                // 版权
                .license("沃德健管云平台V1.0.0")
                // 版权地址
                .licenseUrl("http://www.world.com")
                // 联系人信息
                .contact(new Contact("沃德健管云平台", "http://www.world.com", "xxxx@163.com"))
                .build();
    }

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}

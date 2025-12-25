/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.platform.config;

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
    @Bean("MainConfig")
    public Docket mainApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("01.沃德健管云平台接口Main")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.platform.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("SysConfig")
    public Docket sysApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("02.系统模块")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.system.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("DataApiConfig")
    public Docket dataApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("03.基础数据模块")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.data.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("StatisticsApiConfig")
    public Docket statisticsApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("04.统计模块")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.statistics.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("QueryApiConfig")
    public Docket queryApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("05.查询模块")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.query.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("SellCrmApiConfig")
    public Docket sellCmrApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("06.客户-销售模块")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.sellcrm.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("MemberApiConfig")
    public Docket memberApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("07.会员系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.member.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("ReservationApiConfig")
    public Docket reservationApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("08.预约系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.reservation.controller" + splitor + "com.center.medical.reservation.open"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("ReceptionApiConfig")
    public Docket receptionApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("08.前台系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.reception.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("PayApiConfig")
    public Docket payApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("08.支付模块")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.pay.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("AbteilungApiConfig")
    public Docket abteilungApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("09.科室系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.abteilung.controller" + splitor + "com.center.medical.abteilung.open"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("ReportApiConfig")
    public Docket reportApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("10.总检/报告系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.report.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("SyncPfApiConfig")
    public Docket syncPfApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("11.数据同步系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.syncpf.controller" + splitor + "com.center.medical.syncpf.open"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("PacsApiConfig")
    public Docket pacsApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("13.PACS_LIS系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.pacslis.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    // 创建Docket存入容器，Docket代表一个接口文档
    @Bean("PacsConfig")
    public Docket pacsConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("14.PACS独立系统")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.pacs.controller" + splitor + "com.center.medical.pacs.open"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("FinanceApiConfig")
    public Docket financeApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("15.财务系统")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.finance.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    //
    @Bean("DatascreenApiConfig")
    public Docket datascreenApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                .groupName("16.数据可视化大屏")
                .select()
                .apis(basePackage("com.center.medical.datascreen.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //
    @Bean("QuartzApiConfig")
    public Docket quartzApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                .groupName("17.定时任务模块接口")
                .select()
                .apis(basePackage("com.center.medical.quartz.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("GeneratorApiConfig")
    public Docket searchApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())
                .groupName("18.代码生成器接口")
                .select()
                .apis(basePackage("com.center.medical.generator.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    @Bean("MachineConfig")
    public Docket machineConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("19.自助登记机系统")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.machine.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("appBackendConfig")
    public Docket appBackendConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("21.小程序后台系统")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.appadmin.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }



    @Bean("WorkflowConfig")
    public Docket workflowConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .groupName("20.工作流模块")
                .select()
                // 指定@ApiOperation标注的接口被加入文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.workflow.controller"
                ))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean("MessageApiConfig")
    public Docket messageApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                .groupName("21.消息队列")
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                //  .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里指定Controller扫描包路径
                .apis(basePackage(
                        "com.center.medical.messagepf.controller" + splitor + "com.center.medical.messagepf.open"
                ))
                .paths(PathSelectors.any())
                .build();
    }


    // 创建接口文档的具体信息，会显示在接口文档页面中
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("沃德健管云平台后端接口api")
                // 文档描述
                .description("沃德健管云平台后端接口api文档Swagger版")
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

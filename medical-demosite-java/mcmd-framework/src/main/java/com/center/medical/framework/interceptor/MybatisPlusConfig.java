package com.center.medical.framework.interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.center.medical.common.handler.MyMetaObjectHandler;
import com.center.medical.framework.handler.MyDataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.TimeZone;

/**
 * 程序注解配置
 *
 * @author 路飞
 */
@Slf4j
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("com.center.medical.**.dao")
public class MybatisPlusConfig {

    /**
     * 时区配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }

    /**
     * 逻辑删除插件
     *
     * @return LogicSqlInjector
     */
    @Bean
//    @ConditionalOnMissingBean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
//        return new CustomSqlInjector();
    }

    /**
     * mybatis-plus插件
     */
    @Bean
    public MybatisPlusInterceptor optimisticLockerInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());// 添加数据权限插件
        BranchScopeInterceptor branchScopeInterceptor = new BranchScopeInterceptor();
        // 添加自定义的数据权限处理器
        branchScopeInterceptor.setDataPermissionHandler(new MyDataPermissionHandler());
        mybatisPlusInterceptor.addInnerInterceptor(branchScopeInterceptor);

        return mybatisPlusInterceptor;
    }

    @Bean
    public SyncDataInterceptor mySyncDataInterceptor(MyMetaObjectHandler myMetaObjectHandler) {
        return new SyncDataInterceptor(myMetaObjectHandler);
    }

    /**
     * 自定义SQL注入方法
     *
     * @return
     */
//    @Bean
//    public CustomSqlInjector customSqlInjector() {
//        return new CustomSqlInjector();
//    }
}

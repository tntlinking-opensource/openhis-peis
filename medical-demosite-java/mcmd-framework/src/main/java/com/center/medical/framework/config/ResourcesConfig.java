package com.center.medical.framework.config;

import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.framework.interceptor.IpAuthInterceptor;
import com.center.medical.framework.interceptor.RepeatSubmitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 通用配置
 *
 * @author 路飞
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Resource
    private RepeatSubmitInterceptor repeatSubmitInterceptor;
    @Resource
    private ZhongkangConfig zhongkangConfig;
    @Resource
    private IpAuthInterceptor ipAuthInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String fileRoot = zhongkangConfig.getProfile();
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("当前系统：" + osName);
        if (osName.contains("linux")) {
            // Linux系统
            fileRoot = zhongkangConfig.getProfileLinux();
        }

        /** 本地文件上传路径 */
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + fileRoot + "/");

        /** swagger配置 */
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
        //ip认证拦截器注册
        registry.addInterceptor(ipAuthInterceptor).addPathPatterns("/open/api/**");
        //标准开放接口认证拦截器注册
        registry.addInterceptor(ipAuthInterceptor).addPathPatterns("/open/api/**");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
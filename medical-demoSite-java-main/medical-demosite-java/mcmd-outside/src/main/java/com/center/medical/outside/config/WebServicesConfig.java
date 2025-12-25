package com.center.medical.outside.config;

import com.center.medical.outside.interceptor.OpenApiV4Interceptor;
import com.center.medical.outside.service.BoyingService;
import com.center.medical.outside.service.WSLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * 第三方WebService账号登录接口
 *
 * @author 路飞船长
 * @since 2025-06-12 19:06:50
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebServicesConfig {
    private final WSLoginService wsLoginService;
    private final BoyingService boyingService;
    private final OpenApiV4Interceptor openApiV4Interceptor;
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint wsLoginEndpoint() {

        EndpointImpl endpoint = new EndpointImpl(springBus(), wsLoginService);
        endpoint.publish("/login");
        return endpoint;
    }

    @Bean
    public Endpoint boyingEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), boyingService);
        try {
            endpoint.publish("/boying");
            //进行权鉴校验
            endpoint.getInInterceptors().add(openApiV4Interceptor);
        } catch (Exception e) {
            e.printStackTrace(); // 打印 ServiceConstructionException 的 cause，能看到哪个类有问题
            throw e;
        }

        return endpoint;
    }

    /**
     * 配置CXF Servlet
     * tip: Spring 只允许一个 ServletRegistrationBean 注册相同类型的 Servlet，因此后面的配置会覆盖前面的配置。
     * 结果就是：只有一个 CXF Servlet 被真正注册并生效（通常是最后定义的那个），另一个接口无法访问。
     * @return
     */
    @Bean
    public ServletRegistrationBean<?> cxfServlet() {
        ServletRegistrationBean<CXFServlet> servlet = new ServletRegistrationBean<>(new CXFServlet(), "/open/api/v4/*");
        servlet.setLoadOnStartup(1);
        return servlet;
    }
}

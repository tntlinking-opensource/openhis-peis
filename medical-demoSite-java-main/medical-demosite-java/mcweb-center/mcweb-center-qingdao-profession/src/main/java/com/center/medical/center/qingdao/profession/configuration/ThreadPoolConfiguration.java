package com.center.medical.center.qingdao.profession.configuration;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.jackson.JacksonMsgConvertor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfiguration {

    /**
     * 核心线程数（默认线程数）
     */
    public static final int corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 最大线程数
     */
    public static final int maxPoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private static final int keepAliveTime = 1 * 60 * 60 * 1000;
    /**
     * 缓冲队列大小
     */
    private static final int queueCapacity = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * 线程池名前缀
     */
    private static final String threadNamePrefix = "hdl-uhi-service-";

    @Bean("taskExecutor2") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);

        // 线程池对拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    @SneakyThrows
    @Bean
    public HTTP http() {
        X509TrustManager myTrustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        SSLContext sslCtx = SSLContext.getInstance("TLS");
        sslCtx.init(null, new TrustManager[]{myTrustManager}, new SecureRandom());

        SSLSocketFactory mySSLSocketFactory = sslCtx.getSocketFactory();
        return HTTP.builder().config(builder -> {
            builder.sslSocketFactory(mySSLSocketFactory, myTrustManager);
            builder.hostnameVerifier(myHostnameVerifier);
            //解决okhttps频繁爆出 Caused by: java.net.SocketTimeoutException: Read timed out，原来是默认
            //20s已经很少了，但还是有个别的会报这个错误
            // 连接超时时间（默认10秒）
            builder.connectTimeout(60, TimeUnit.SECONDS);
            // 写入超时时间（默认10秒）
            builder.writeTimeout(60, TimeUnit.SECONDS);
            // 读取超时时间（默认10秒）
            builder.readTimeout(60, TimeUnit.SECONDS);

            //原来就是注释掉的
//            Duration duration = Duration.ofMillis(2);
//            builder.callTimeout(duration);
//            builder.connectTimeout(duration);
//            builder.readTimeout(duration);
//            builder.writeTimeout(duration);
        }).addMsgConvertor(new JacksonMsgConvertor()).build();
    }
}

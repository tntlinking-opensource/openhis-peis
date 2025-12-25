package com.center.medical.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * 启用Spring-Retry
 * @author xhp
 * @since 2023-11-22 7:36
 */
@Configuration
@EnableRetry
public class RetryConfig {
}

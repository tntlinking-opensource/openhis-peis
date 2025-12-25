package com.center.medical.common.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.*;

/**
 * @author: 路飞船长
 * @date: 2023/3/22 10:21
 * @description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface InterceptorIgnore {
    public String tenantLine() default "";
}

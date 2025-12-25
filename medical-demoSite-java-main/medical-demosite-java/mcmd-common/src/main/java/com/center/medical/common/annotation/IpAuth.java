package com.center.medical.common.annotation;

import java.lang.annotation.*;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:33
 * @description: 接口请求的IP认证
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IpAuth {
    String value() default "";
}

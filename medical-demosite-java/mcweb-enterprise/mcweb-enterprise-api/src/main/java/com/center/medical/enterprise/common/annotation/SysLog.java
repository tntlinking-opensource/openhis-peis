/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志注解
 * @author yami
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}

package com.center.medical.common.annotation;

import java.lang.annotation.*;

/**
 * 岗位权限过滤注解
 *
 * @author 路飞
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostScope {
    /**
     * 管理员过滤字段，如普通套餐下的销售经理：cm.xsjlid
     */
    public String alias() default "";
}

package com.center.medical.common.annotation;

import java.lang.annotation.*;

/**
 * 分中心权限过滤注解
 *
 * @author 路飞
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BranchScope {
    /**
     * 分中心过滤字段，如预约管理表：r.branch_id
     */
    public String alias() default "";
}

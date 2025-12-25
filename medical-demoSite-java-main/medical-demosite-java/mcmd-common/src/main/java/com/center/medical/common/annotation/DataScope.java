package com.center.medical.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author 路飞
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 分中心表的别名，格式：别名,字段名
     */
    public String branchAlias() default "";

    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名: u:user_id(用户ID)、uno:user_no（用户编号）
     */
    public String userAlias() default "";

    /**
     * 权限字符（用于多个角色匹配符合要求的权限）默认根据权限注解@ss获取，多个权限用逗号分隔开来
     */
    public String permission() default "";

    /**
     * 岗位表别名
     */
    public String postAlias() default "";
}

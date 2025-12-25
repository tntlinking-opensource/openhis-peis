/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 使用redis进行分布式锁
 * @author yami
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

	/**
	 * redis锁 名字
	 */
	String lockName() default "";

	/**
	 * redis锁 key 支持spel表达式
	 */
	String key() default "";

	/**
	 * 过期毫秒数,默认为5000毫秒
	 *
	 * @return 轮询锁的时间
	 */
	int expire() default 5000;

	/**
	 * 超时时间单位
	 *
	 * @return 秒
	 */
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}

package com.center.medical.common.aspect;

import cn.hutool.core.util.StrUtil;
import com.center.medical.common.annotation.RedisLock;
import com.center.medical.common.utils.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author
 */
@Slf4j
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedissonClient redissonClient;

    private static final String REDISSON_LOCK_PREFIX = "redisson_lock:";

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        log.info("分布式锁切面RedisLockAspect，开始加锁redisLock：{}", redisLock);
        String spel = redisLock.key();
        String lockName = redisLock.lockName();
        RLock rLock = redissonClient.getLock(getRedisKey(joinPoint, lockName, spel));
        Object result = null;

        try {
            rLock.lock(redisLock.expire(), redisLock.timeUnit());
            log.info("分布式锁切面RedisLockAspect，加锁成功开始执行方法rLock：{}", rLock);
            //执行方法
            result = joinPoint.proceed();
            log.info("分布式锁切面RedisLockAspect，执行方法结束result：{}", result);

        } finally {
            if (rLock.isLocked()) {
                log.info("分布式锁切面RedisLockAspect，执行方法结束解锁开始finally");
                rLock.unlock();
            }
        }
        return result;
    }

    /**
     * 将spel表达式转换为字符串
     *
     * @param joinPoint 切点
     * @return redisKey
     */
    private String getRedisKey(ProceedingJoinPoint joinPoint, String lockName, String spel) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        return REDISSON_LOCK_PREFIX + lockName + StrUtil.COLON + SpelUtil.parse(target, spel, targetMethod, arguments);
    }
}

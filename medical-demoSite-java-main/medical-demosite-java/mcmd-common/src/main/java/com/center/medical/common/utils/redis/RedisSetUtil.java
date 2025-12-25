/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.utils.redis;

import com.center.medical.common.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 *
 * @author 路飞
 */
@Slf4j
public class RedisSetUtil {

    @SuppressWarnings("unchecked")
    private static RedisTemplate<String, Object> redisTemplate = SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);

    private static StringRedisTemplate stringRedisTemplate = SpringContextUtils.getBean("stringRedisTemplate", StringRedisTemplate.class);


    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static Boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("指定Redis缓存失效时间错误：", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回-1代表为永久有效 失效时间为0，说明该主键未设置失效时间（失效时间默认为-1）
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 添加
     *
     * @param key
     * @param value
     * @param score
     */
    public static void addToSortedSet(String key, Object value, Long score) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, value, score);
    }

    /**
     * 按序号区间查询，如：查第一个(start=0 end=0) 查2个(start=0 end=1)，start为空则表示从0开始，end为空则表示查询至最后一个
     *
     * @param key
     * @param start 开始序号（从0开始，0表是第一个）
     * @param end   截至序号
     * @return
     */
    public static Set<Object> queryByRange(String key, Long start, Long end) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        if (Objects.nonNull(start) && Objects.nonNull(end)) {
            return zSetOperations.range(key, start, end);
        } else if (Objects.nonNull(start)) {
            return zSetOperations.range(key, start, zSetOperations.size(key));
        } else if (Objects.nonNull(end)) {
            return zSetOperations.range(key, 0, end);
        } else {
            return zSetOperations.range(key, 0, zSetOperations.size(key));
        }
    }

    /**
     * 按时间戳区间查询
     *
     * @param key
     * @param startTime
     * @param endTime
     * @return
     */
    public static Set<Object> queryByTimeRange(String key, Long startTime, Long endTime) {
//        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//        return zSetOperations.rangeByScore(key, startTime, endTime);
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
            return zSetOperations.rangeByScore(key, startTime, endTime);
        } else if (Objects.nonNull(startTime)) {
            return zSetOperations.rangeByScore(key, startTime, Double.POSITIVE_INFINITY);
        } else if (Objects.nonNull(endTime)) {
            return zSetOperations.rangeByScore(key, Double.NEGATIVE_INFINITY, endTime);
        } else {
            return zSetOperations.rangeByScore(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
    }

    /**
     * 删除set集合中的数据，按序号删除，如：查第一个(start=0 end=0) 查2个(start=0 end=1)
     *
     * @param key   redis的Key
     * @param start 开始序号（从0开始，0表是第一个）
     * @param end   截至序号（从0开始，0表是第一个）
     * @return
     */
    public static Long removeRange(String key, Long start, Long end) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.removeRange(key, start, end);
    }

    /**
     * 删除set集合中的数据
     *
     * @param key  redis的Key
     * @param item 删除的对象
     * @return
     */
    public static Long remove(String key, Object item) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.remove(key, item);
    }

    /**
     * 查询数量
     *
     * @param key redis的Key
     * @return
     */
    public static Long count(String key) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.size(key);
    }

}

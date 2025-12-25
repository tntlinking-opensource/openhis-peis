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

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.config.SyncRedisConfig;
import com.center.medical.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * 缓存工具类
 *
 * @author 路飞
 */
@Slf4j
public class SyncRedisUtil {

    /**
     * 创建RedissonClient对象
     */
    @SuppressWarnings("unchecked")
    public static RedissonClient redis() {
        String str = RedisUtil.get(Constants.SYNC_REDIS_OFF_CONFIG);
        SyncRedisConfig syncRedisConfig = JSONUtil.toBean(str, SyncRedisConfig.class);
        // 连接Redis
        String host = syncRedisConfig.getHost();
        int port = syncRedisConfig.getPort();
        String password = syncRedisConfig.getPassword();
        int database = syncRedisConfig.getDatabase();
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setDatabase(database);
        if (StrUtil.isNotBlank(password)) {
            config.useSingleServer().setPassword(password);
        }
        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(RedissonClient redisson, String key) {
        return key == null ? null : (T) redis().getBucket(key).get();
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(RedissonClient redisson, String key, Object value, long time) {
        try {
            if (time > 0) {
                redis().getBucket(key).set(value);
            } else {
                redis().getBucket(key).set(value);
            }
            return true;
        } catch (Exception e) {
            log.error("设置Redis缓存错误:", e);
            return false;
        }
    }


}

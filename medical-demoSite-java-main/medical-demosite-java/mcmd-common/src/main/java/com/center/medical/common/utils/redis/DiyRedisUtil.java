package com.center.medical.common.utils.redis;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis连接工具类（Redisson版）
 * 支持自定义连接参数
 * 功能：keys、get、set、dels、ping
 * 线程安全，支持连接关闭
 */
public class DiyRedisUtil {

    private final RedissonClient redissonClient;

    /**
     * 构造函数 - 传入连接参数创建连接
     *
     * @param host     Redis主机
     * @param port     Redis端口
     * @param password Redis密码（可为null）
     * @param database Redis数据库序号（0~15）
     */
    public DiyRedisUtil(String host, int port, String password, int database) {
        // 1️⃣ 配置 Redisson
        Config config = new Config();
        String address = String.format("redis://%s:%d", host, port);
        config.useSingleServer()
                .setAddress(address)
                .setDatabase(database)
                .setConnectionMinimumIdleSize(4)
                .setConnectionPoolSize(16);

        if (password != null && !password.isEmpty()) {
            config.useSingleServer().setPassword(password);
        }

        // 2️⃣ 创建 Redisson 客户端
        this.redissonClient = Redisson.create(config);
    }

    /** 获取所有匹配的key（模糊匹配） */
    public Set<String> keys(String pattern) {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> iterable = keys.getKeysByPattern(pattern);
        Set<String> result = new HashSet<>();
        for (String key : iterable) {
            result.add(key);
        }
        return result;
    }

    /** 获取指定key的值 */
    public String get(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /** 设置key-value，可带过期时间（秒） */
    public void set(String key, String value, int expireSeconds) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        if (expireSeconds > 0) {
            bucket.set(value, expireSeconds, TimeUnit.SECONDS);
        } else {
            bucket.set(value);
        }
    }

    /** 删除多个key */
    public long dels(String... keys) {
        RKeys rKeys = redissonClient.getKeys();
        return rKeys.delete(keys);
    }

    /** 测试连接是否可用 */
    public boolean ping() {
        try {
            // 使用 RKeys 获取任意 key 来检测连接
            redissonClient.getKeys();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** 关闭连接 */
    public void close() {
        if (redissonClient != null && !redissonClient.isShutdown()) {
            redissonClient.shutdown();
        }
    }

    /** 示例测试 */
    public static void main(String[] args) {
        DiyRedisUtil redisUtil = new DiyRedisUtil("123.249.111.34", 6679, "zkr@0825", 1);

        System.out.println("Redis 连接测试: " + redisUtil.ping());

//        redisUtil.set("redisson:key", "Hello Redisson!", 60);
//        System.out.println("get redisson:key = " + redisUtil.get("redisson:key"));

        Set<String> keys = redisUtil.keys("redisson*");
//        System.out.println("匹配到的keys: " + keys);

//        long deleted = redisUtil.dels("redisson:key");
//        System.out.println("删除数量: " + deleted);

        redisUtil.close();
    }
}

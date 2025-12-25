package com.center.medical.common.config;

import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.redis.RedisUtil;
import org.springframework.context.annotation.Configuration;

/**
 * 同步数据表 配置属性
 *
 * @author 路飞
 */
@Configuration
public class SyncTableConfig {

    public String getOnline() {
        return RedisUtil.get(Constants.SYNC_TABLES_ONLINE);
    }

    public String getOffline() {
        return RedisUtil.get(Constants.SYNC_TABLES_OFFLINE);
    }

}

package com.center.medical.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 路飞
 * @date: 2022-11-04 21:16
 * @description: 加载配置文件属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "sync")
public class SyncProperties {
    /**
     * 是否开启同步功能：0.关闭 1.开启
     */
    public Integer open;
}

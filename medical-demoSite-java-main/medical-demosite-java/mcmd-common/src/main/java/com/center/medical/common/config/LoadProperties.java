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
@ConfigurationProperties(prefix = "project")
public class LoadProperties {
    public String name;

    public Integer online;
}

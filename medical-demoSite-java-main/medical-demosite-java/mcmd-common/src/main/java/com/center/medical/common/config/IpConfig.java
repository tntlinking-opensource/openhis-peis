package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-02-07 16:13
 * @description: 授权IP配置
 */
@Data
public class IpConfig implements Serializable {
    private static final long serialVersionUID = -2280967345068521188L;
    //平台系统获取数据同步的授权IP
    private String syncAuthIp;
}

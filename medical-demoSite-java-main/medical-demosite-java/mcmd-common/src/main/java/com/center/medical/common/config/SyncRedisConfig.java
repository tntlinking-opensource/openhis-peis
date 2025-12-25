package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 读取项目相关配置
 *
 * @author 路飞
 */
@Data
public class SyncRedisConfig implements Serializable {

    private static final long serialVersionUID = -1613942396205738567L;
    /**
     * redis地址
     */
    private String host;
    /**
     * redis端口
     */
    private int port;
    /**
     * 密码
     */
    private String password;
    /**
     * 库号
     */
    private int database;

}

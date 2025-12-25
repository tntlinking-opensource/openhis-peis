package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 瑞林萨尔健康管理系统对接配置
 * @author xhp
 * @since 2025-03-21 15:17
 */
@Data
public class RilinConfig implements Serializable {
    //是否开启基础数据同步线程
    private Boolean isBasicOpen;
    //是否开启体检者数据同步线程
    private Boolean isPatientOpen;
    //是否开启重大阳性指标同步线程
    private Boolean isRiskOpen;
    //是否限制运行线程的ip
    private Boolean isIpAuth;
    //允许运行线程的ip
    private String ip;
}

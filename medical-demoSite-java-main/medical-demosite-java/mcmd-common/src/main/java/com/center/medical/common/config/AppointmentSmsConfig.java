package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 预约短信配置
 *
 * @author ay
 * @since 2024-01-06 18:07
 */
@Data
public class AppointmentSmsConfig implements Serializable {
    private static final long serialVersionUID = -5621997088567163994L;
    /**
     * 是否开启
     */
    private Boolean isOpen;

    /**
     * 没预约能否登记
     */
    private Boolean registration;
}

package com.center.medical.common.config;

import lombok.Data;

/**
 * 老系统配置
 * @author xhp
 * @since 2024-01-11 8:07
 */
@Data
public class OldSystemConfig {

    //是否开启新老系统集成预约
    private Integer isIntegratedReservationEnabled;

}

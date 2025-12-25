package com.center.medical.outreach.pf.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 文达通获取报告配置
 * @author xhp
 * @since 2023-10-26 9:43
 */
@Data
public class WendatongReportConfig implements Serializable {
    /**
     * 是否开放接口
     */
    private Integer isOpen;

    /**
     * 开放获取指定时间之前的报告
     */
    private String endDate;

    /**
     * 是否开放只查询瑞源体检号的接口
     */
    private Integer isRyOpen;
}

package com.center.medical.system.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysJobDto implements Serializable {

    private static final long serialVersionUID = 123126420623080154L;
    /**
     * 任务ID
     */
    private Long jobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * cron计划策略
     */
    private String misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 任务状态（0正常 1暂停）
     */
    private String status;

    /**
     * 备注信息：用来存允许调用的IP地址
     */
    private String remark;
}

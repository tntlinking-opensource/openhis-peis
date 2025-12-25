package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 已触发的触发器表(QrtzFiredTriggers)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Data
@TableName("qrtz_fired_triggers")
@ApiModel(value = "QrtzFiredTriggers", description = "已触发的触发器表实体类")
public class QrtzFiredTriggers extends Model<QrtzFiredTriggers> implements Serializable {
    private static final long serialVersionUID = 867023417086872734L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "调度器实例id")
    private String entryId;

    @ApiModelProperty(value = "qrtz_triggers表trigger_name的外键")
    private String triggerName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_group的外键")
    private String triggerGroup;

    @ApiModelProperty(value = "调度器实例名")
    private String instanceName;

    @ApiModelProperty(value = "触发的时间")
    private Long firedTime;

    @ApiModelProperty(value = "定时器制定的时间")
    private Long schedTime;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "是否并发")
    private String isNonconcurrent;

    @ApiModelProperty(value = "是否接受恢复执行")
    private String requestsRecovery;
}

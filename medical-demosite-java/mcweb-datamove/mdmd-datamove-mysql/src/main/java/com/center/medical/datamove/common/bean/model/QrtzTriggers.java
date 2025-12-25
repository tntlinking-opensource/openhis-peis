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
 * 触发器详细信息表(QrtzTriggers)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
@Data
@TableName("qrtz_triggers")
@ApiModel(value = "QrtzTriggers", description = "触发器详细信息表实体类")
public class QrtzTriggers extends Model<QrtzTriggers> implements Serializable {
    private static final long serialVersionUID = 603682855915901668L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "触发器的名字")
    private String triggerName;

    @ApiModelProperty(value = "触发器所属组的名字")
    private String triggerGroup;

    @ApiModelProperty(value = "qrtz_job_details表job_name的外键")
    private String jobName;

    @ApiModelProperty(value = "qrtz_job_details表job_group的外键")
    private String jobGroup;

    @ApiModelProperty(value = "相关介绍")
    private String description;

    @ApiModelProperty(value = "上一次触发时间（毫秒）")
    private Long nextFireTime;

    @ApiModelProperty(value = "下一次触发时间（默认为-1表示不触发）")
    private Long prevFireTime;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "触发器状态")
    private String triggerState;

    @ApiModelProperty(value = "触发器的类型")
    private String triggerType;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "日程表名称")
    private String calendarName;

    @ApiModelProperty(value = "补偿执行的策略")
    private Integer misfireInstr;

    @ApiModelProperty(value = "存放持久化job对象")
    private Object jobData;
}

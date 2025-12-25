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
 * 简单触发器的信息表(QrtzSimpleTriggers)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
@Data
@TableName("qrtz_simple_triggers")
@ApiModel(value = "QrtzSimpleTriggers", description = "简单触发器的信息表实体类")
public class QrtzSimpleTriggers extends Model<QrtzSimpleTriggers> implements Serializable {
    private static final long serialVersionUID = -54649266138423721L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_name的外键")
    private String triggerName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_group的外键")
    private String triggerGroup;

    @ApiModelProperty(value = "重复的次数统计")
    private Long repeatCount;

    @ApiModelProperty(value = "重复的间隔时间")
    private Long repeatInterval;

    @ApiModelProperty(value = "已经触发的次数")
    private Long timesTriggered;
}

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
 * 调度器状态表(QrtzSchedulerState)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Data
@TableName("qrtz_scheduler_state")
@ApiModel(value = "QrtzSchedulerState", description = "调度器状态表实体类")
public class QrtzSchedulerState extends Model<QrtzSchedulerState> implements Serializable {
    private static final long serialVersionUID = -48210217716107414L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "实例名称")
    private String instanceName;

    @ApiModelProperty(value = "上次检查时间")
    private Long lastCheckinTime;

    @ApiModelProperty(value = "检查间隔时间")
    private Long checkinInterval;
}

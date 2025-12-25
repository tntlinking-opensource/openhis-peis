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
 * 任务详细信息表(QrtzJobDetails)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Data
@TableName("qrtz_job_details")
@ApiModel(value = "QrtzJobDetails", description = "任务详细信息表实体类")
public class QrtzJobDetails extends Model<QrtzJobDetails> implements Serializable {
    private static final long serialVersionUID = -36343675286057541L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "相关介绍")
    private String description;

    @ApiModelProperty(value = "执行任务类名称")
    private String jobClassName;

    @ApiModelProperty(value = "是否持久化")
    private String isDurable;

    @ApiModelProperty(value = "是否并发")
    private String isNonconcurrent;

    @ApiModelProperty(value = "是否更新数据")
    private String isUpdateData;

    @ApiModelProperty(value = "是否接受恢复执行")
    private String requestsRecovery;

    @ApiModelProperty(value = "存放持久化job对象")
    private Object jobData;
}

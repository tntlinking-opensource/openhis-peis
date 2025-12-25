package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 定时任务调度日志表(SysJobLog)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Data
@TableName("sys_job_log")
@ApiModel(value = "SysJobLog", description = "定时任务调度日志表实体类")
public class SysJobLog extends Model<SysJobLog> implements Serializable {
    private static final long serialVersionUID = 135625567929849506L;

    @TableId(value = "job_log_id")
    @ApiModelProperty(value = "任务日志ID")
    private Long jobLogId;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串")
    private String invokeTarget;

    @ApiModelProperty(value = "日志信息")
    private String jobMessage;

    @ApiModelProperty(value = "执行状态（0正常 1失败）")
    private String status;

    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}

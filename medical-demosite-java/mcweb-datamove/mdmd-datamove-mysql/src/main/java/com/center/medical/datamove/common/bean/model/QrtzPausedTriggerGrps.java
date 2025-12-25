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
 * 暂停的触发器表(QrtzPausedTriggerGrps)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Data
@TableName("qrtz_paused_trigger_grps")
@ApiModel(value = "QrtzPausedTriggerGrps", description = "暂停的触发器表实体类")
public class QrtzPausedTriggerGrps extends Model<QrtzPausedTriggerGrps> implements Serializable {
    private static final long serialVersionUID = 182861952574531355L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_group的外键")
    private String triggerGroup;
}

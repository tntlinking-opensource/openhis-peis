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
 * Blob类型的触发器表(QrtzBlobTriggers)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Data
@TableName("qrtz_blob_triggers")
@ApiModel(value = "QrtzBlobTriggers", description = "Blob类型的触发器表实体类")
public class QrtzBlobTriggers extends Model<QrtzBlobTriggers> implements Serializable {
    private static final long serialVersionUID = 271954521644188671L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_name的外键")
    private String triggerName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_group的外键")
    private String triggerGroup;

    @ApiModelProperty(value = "存放持久化Trigger对象")
    private Object blobData;
}

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
 * 存储的悲观锁信息表(QrtzLocks)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Data
@TableName("qrtz_locks")
@ApiModel(value = "QrtzLocks", description = "存储的悲观锁信息表实体类")
public class QrtzLocks extends Model<QrtzLocks> implements Serializable {
    private static final long serialVersionUID = 130985177376181324L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "悲观锁名称")
    private String lockName;
}

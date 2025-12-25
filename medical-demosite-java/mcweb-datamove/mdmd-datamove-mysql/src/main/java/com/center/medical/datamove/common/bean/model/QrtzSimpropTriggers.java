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
 * 同步机制的行锁表(QrtzSimpropTriggers)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
@Data
@TableName("qrtz_simprop_triggers")
@ApiModel(value = "QrtzSimpropTriggers", description = "同步机制的行锁表实体类")
public class QrtzSimpropTriggers extends Model<QrtzSimpropTriggers> implements Serializable {
    private static final long serialVersionUID = 166978542712650043L;

    @TableId(value = "sched_name", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "调度名称")
    private String schedName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_name的外键")
    private String triggerName;

    @ApiModelProperty(value = "qrtz_triggers表trigger_group的外键")
    private String triggerGroup;

    @ApiModelProperty(value = "String类型的trigger的第一个参数")
    private String strProp1;

    @ApiModelProperty(value = "String类型的trigger的第二个参数")
    private String strProp2;

    @ApiModelProperty(value = "String类型的trigger的第三个参数")
    private String strProp3;

    @ApiModelProperty(value = "int类型的trigger的第一个参数")
    private Integer intProp1;

    @ApiModelProperty(value = "int类型的trigger的第二个参数")
    private Integer intProp2;

    @ApiModelProperty(value = "long类型的trigger的第一个参数")
    private Long longProp1;

    @ApiModelProperty(value = "long类型的trigger的第二个参数")
    private Long longProp2;

    @ApiModelProperty(value = "decimal类型的trigger的第一个参数")
    private Double decProp1;

    @ApiModelProperty(value = "decimal类型的trigger的第二个参数")
    private Double decProp2;

    @ApiModelProperty(value = "Boolean类型的trigger的第一个参数")
    private String boolProp1;

    @ApiModelProperty(value = "Boolean类型的trigger的第二个参数")
    private String boolProp2;
}

package com.center.medical.message.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/3/27 09:45
 * @description: 消费者消费消息的参数
 */
@Data
public class UpdateCondition implements Serializable {
    private static final long serialVersionUID = 2100335159988506957L;

    @ApiModelProperty(value = "数据库名")
    private String dbName;

    @ApiModelProperty(value = "当前表名")
    private String curTableName;

    @ApiModelProperty(value = "目标表名")
    private String tableName;

    @ApiModelProperty(value = "条件字段名,用于取当前数据条件值")
    private String conditionKeyName;

    @ApiModelProperty(value = "条件字段名类型：1.数值类型 2.字符类型")
    private Integer conditionKeyType;

    @ApiModelProperty(value = "目标表条件字段名")
    private String keyName;

    @ApiModelProperty(value = "值字段名,多个以英文逗号隔开")
    private String valueNames;

}

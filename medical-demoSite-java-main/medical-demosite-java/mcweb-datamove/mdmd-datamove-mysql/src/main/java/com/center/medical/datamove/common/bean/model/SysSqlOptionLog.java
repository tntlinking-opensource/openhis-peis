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
 * SQL操作日志表(SysSqlOptionLog)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
@Data
@TableName("sys_sql_option_log")
@ApiModel(value = "SysSqlOptionLog", description = "SQL操作日志表实体类")
public class SysSqlOptionLog extends Model<SysSqlOptionLog> implements Serializable {
    private static final long serialVersionUID = 148242912668571851L;

    @TableId(value = "id")
    @ApiModelProperty(value = "操作日志的唯一标识")
    private Long id;

    @ApiModelProperty(value = "执行的SQL语句")
    private String sqlStatement;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作时间")
    private Date operationTime;
}

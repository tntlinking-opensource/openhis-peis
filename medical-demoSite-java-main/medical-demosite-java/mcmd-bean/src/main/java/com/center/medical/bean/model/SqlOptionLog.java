package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * SQL操作日志表(SqlOptionLog)表实体类
 *
 * @author 路飞船长
 * @since 2023-06-21 14:32:15
 */
@Data
@TableName("sys_sql_option_log")
@ApiModel(value = "SqlOptionLog", description = "SQL操作日志表实体类")
public class SqlOptionLog extends Model<SqlOptionLog> implements Serializable {
    private static final long serialVersionUID = 607881601216072957L;

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

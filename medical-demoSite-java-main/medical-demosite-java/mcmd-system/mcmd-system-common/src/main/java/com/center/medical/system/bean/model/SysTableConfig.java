package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据表配置(SysTableConfig)表实体类
 *
 * @author makejava
 * @since 2023-07-12 11:41:24
 */
@Data
@TableName("sys_table_config")
@ApiModel(value = "SysTableConfig", description = "数据表配置实体类")
public class SysTableConfig extends Model<SysTableConfig> implements Serializable {
    private static final long serialVersionUID = 743445980254966194L;

    @TableId(value = "id")
    @ApiModelProperty(value = "数据表ID")
    private Integer id;

    @ApiModelProperty(value = "数据库名")
    private String dbName;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "主键字段名")
    private String keyName;

    @ApiModelProperty(value = "分中心字段名")
    private String cidName;

    @ApiModelProperty(value = "关联表sys_table_config的ID")
    private Integer pid;

    @ApiModelProperty(value = "关联表的关联字段名称（查分中心ID）")
    private String pkeyName;

    @ApiModelProperty(value = "当前表的关联字段的名称（查分中心ID）")
    private String curKeyName;

    @ApiModelProperty(value = "同步类型：1.线上至线下 2.线下至线上 3.双向")
    private Integer syncType;

    @ApiModelProperty(value = "是否需要同步：0.不需要 1.同步至所有中心 2.指定中心")
    private Integer needSync;

    @ApiModelProperty(value = "同步筛选条件，所变化的数据必须满足这个条件")
    private Integer syncCondition;

    @ApiModelProperty(value = "更新条件，用于判断是否执行同步更新操作")
    @TableField(exist = false)
    private String updateCondition;

    @ApiModelProperty(value = "存储文件的字段，多个以逗号隔开")
    private String fileKeyName;

    @ApiModelProperty(value = "是否即时同步：0.否 1.是")
    private Integer syncNow;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}

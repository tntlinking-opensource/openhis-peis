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
 * 参数配置表(SysConfig)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Data
@TableName("sys_config")
@ApiModel(value = "SysConfig", description = "参数配置表实体类")
public class SysConfig extends Model<SysConfig> implements Serializable {
    private static final long serialVersionUID = 444933144412051882L;

    @TableId(value = "config_id")
    @ApiModelProperty(value = "参数主键")
    private Integer configId;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "系统内置（Y是 N否）")
    private String configType;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}

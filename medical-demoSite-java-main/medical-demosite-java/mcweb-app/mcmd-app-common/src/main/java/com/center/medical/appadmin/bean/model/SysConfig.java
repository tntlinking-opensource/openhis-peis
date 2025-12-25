package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置信息表(SysConfig)表实体类
 *
 * @author ay
 * @since 2024-03-19 17:40:42
 */
@Data
@TableName("md_sys_config")
@ApiModel(value = "SysConfig", description = "系统配置信息表实体类")
public class SysConfig extends Model<SysConfig> implements Serializable {
    private static final long serialVersionUID = 948621362679560593L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Long id;


    @ApiModelProperty(value = "key")
    private String paramKey;


    @ApiModelProperty(value = "value")
    private String paramValue;


    @ApiModelProperty(value = "备注")
    private String remark;

}

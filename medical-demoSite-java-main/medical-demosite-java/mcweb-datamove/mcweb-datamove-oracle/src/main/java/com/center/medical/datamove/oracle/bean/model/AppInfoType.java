package com.center.medical.datamove.oracle.bean.model;


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
 * APP咨询类型(AppInfoType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:39
 */
@Data
@TableName("APP_INFO_TYPE")
@ApiModel(value = "AppInfoType", description = "APP咨询类型实体类")
public class AppInfoType extends Model<AppInfoType> implements Serializable {
    private static final long serialVersionUID = -53787146389483443L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String typeName;

    @ApiModelProperty(value = "${column.comment}")
    private String typeIndex;

    @ApiModelProperty(value = "启用1  禁用0")
    private Integer typeState;
}

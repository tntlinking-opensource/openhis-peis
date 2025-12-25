package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (DrinkingType)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:22
 */
@Data
@TableName("DRINKING_TYPE")
@ApiModel(value = "DrinkingType", description = "$tableInfo.comment实体类")
public class DrinkingType extends Model<DrinkingType> implements Serializable {
    private static final long serialVersionUID = 964285080030929009L;

    @ApiModelProperty(value = "${column.comment}")
    private String typeName;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}

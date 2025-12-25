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
 * (Temporaryqueuehuaou)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:18
 */
@Data
@TableName("TEMPORARYQUEUEHUAOU")
@ApiModel(value = "Temporaryqueuehuaou", description = "$tableInfo.comment实体类")
public class Temporaryqueuehuaou extends Model<Temporaryqueuehuaou> implements Serializable {
    private static final long serialVersionUID = 311349221975734796L;

    @TableId(value = "ID")
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer transmitting;
}

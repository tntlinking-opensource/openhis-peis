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
 * (Temporaryqueuetest)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:20
 */
@Data
@TableName("TEMPORARYQUEUETEST")
@ApiModel(value = "Temporaryqueuetest", description = "$tableInfo.comment实体类")
public class Temporaryqueuetest extends Model<Temporaryqueuetest> implements Serializable {
    private static final long serialVersionUID = 140858401007982094L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer transmitting;
}

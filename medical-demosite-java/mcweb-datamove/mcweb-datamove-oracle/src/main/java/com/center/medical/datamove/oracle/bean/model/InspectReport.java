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
 * (InspectReport)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:16
 */
@Data
@TableName("INSPECT_REPORT")
@ApiModel(value = "InspectReport", description = "$tableInfo.comment实体类")
public class InspectReport extends Model<InspectReport> implements Serializable {
    private static final long serialVersionUID = -27221445509974695L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Date printTime;

    @ApiModelProperty(value = "${column.comment}")
    private String printCount;
}

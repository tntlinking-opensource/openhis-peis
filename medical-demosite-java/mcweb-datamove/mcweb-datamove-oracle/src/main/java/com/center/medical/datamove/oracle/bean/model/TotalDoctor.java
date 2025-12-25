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
 * (TotalDoctor)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:34
 */
@Data
@TableName("TOTAL_DOCTOR")
@ApiModel(value = "TotalDoctor", description = "$tableInfo.comment实体类")
public class TotalDoctor extends Model<TotalDoctor> implements Serializable {
    private static final long serialVersionUID = 369093486000991490L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String totalId;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer type;
}

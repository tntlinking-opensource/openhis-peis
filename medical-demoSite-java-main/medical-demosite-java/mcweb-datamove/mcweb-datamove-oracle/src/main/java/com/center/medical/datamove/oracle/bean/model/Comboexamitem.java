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
 * (Comboexamitem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:42
 */
@Data
@TableName("COMBOEXAMITEM")
@ApiModel(value = "Comboexamitem", description = "$tableInfo.comment实体类")
public class Comboexamitem extends Model<Comboexamitem> implements Serializable {
    private static final long serialVersionUID = -37973487104469432L;

    @ApiModelProperty(value = "${column.comment}")
    private String harmId;

    @ApiModelProperty(value = "${column.comment}")
    private String examId;

    @ApiModelProperty(value = "${column.comment}")
    private String itemId;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String scopeUpper;

    @ApiModelProperty(value = "${column.comment}")
    private String scoperFloor;

    @ApiModelProperty(value = "${column.comment}")
    private String gscopeupper;

    @ApiModelProperty(value = "${column.comment}")
    private String gscoperfloor;

    @ApiModelProperty(value = "${column.comment}")
    private String comboId;

    @ApiModelProperty(value = "${column.comment}")
    private String medicalType;

    @ApiModelProperty(value = "${column.comment}")
    private String ksId;
}

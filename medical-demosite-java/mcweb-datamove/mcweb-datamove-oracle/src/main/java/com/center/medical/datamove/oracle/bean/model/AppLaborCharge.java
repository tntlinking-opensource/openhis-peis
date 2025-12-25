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
 * (AppLaborCharge)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:42
 */
@Data
@TableName("APP_LABOR_CHARGE")
@ApiModel(value = "AppLaborCharge", description = "$tableInfo.comment实体类")
public class AppLaborCharge extends Model<AppLaborCharge> implements Serializable {
    private static final long serialVersionUID = -42719901421617403L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientbizno;

    @ApiModelProperty(value = "${column.comment}")
    private String memo;

    @ApiModelProperty(value = "${column.comment}")
    private Integer type;

    @ApiModelProperty(value = "${column.comment}")
    private String amount;

    @ApiModelProperty(value = "${column.comment}")
    private String balance;

    @ApiModelProperty(value = "${column.comment}")
    private String appUserId;

    @ApiModelProperty(value = "${column.comment}")
    private String patientname;

    @ApiModelProperty(value = "${column.comment}")
    private String orgName;

    @ApiModelProperty(value = "${column.comment}")
    private String examsuiteName;

    @ApiModelProperty(value = "${column.comment}")
    private String numorgresv;

    @ApiModelProperty(value = "${column.comment}")
    private String idTjtc;

    @ApiModelProperty(value = "${column.comment}")
    private String idOrg;

    @ApiModelProperty(value = "${column.comment}")
    private String username;

    @ApiModelProperty(value = "${column.comment}")
    private String operator;
}

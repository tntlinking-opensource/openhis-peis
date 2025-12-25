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
 * (BkPatient)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:49
 */
@Data
@TableName("BK_PATIENT")
@ApiModel(value = "BkPatient", description = "$tableInfo.comment实体类")
public class BkPatient extends Model<BkPatient> implements Serializable {
    private static final long serialVersionUID = -59033631137718146L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientname;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String idcardno;

    @ApiModelProperty(value = "${column.comment}")
    private String orgName;

    @ApiModelProperty(value = "${column.comment}")
    private String idSex;

    @ApiModelProperty(value = "${column.comment}")
    private Date birthdate;

    @ApiModelProperty(value = "${column.comment}")
    private String age;

    @ApiModelProperty(value = "${column.comment}")
    private String marriage;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregister;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamtype;

    @ApiModelProperty(value = "${column.comment}")
    private String examsuiteName;

    @ApiModelProperty(value = "${column.comment}")
    private String orgDepart;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private Integer countreportoccupationxml;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorapply;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fRegistered;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestion;
}

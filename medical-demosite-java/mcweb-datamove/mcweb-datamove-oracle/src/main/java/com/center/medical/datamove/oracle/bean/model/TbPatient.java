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
 * (TbPatient)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:12
 */
@Data
@TableName("TB_PATIENT")
@ApiModel(value = "TbPatient", description = "$tableInfo.comment实体类")
public class TbPatient extends Model<TbPatient> implements Serializable {
    private static final long serialVersionUID = -20508893570310663L;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String idcardno;

    @ApiModelProperty(value = "${column.comment}")
    private String patientname;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String idSex;

    @ApiModelProperty(value = "${column.comment}")
    private Date birthdate;

    @ApiModelProperty(value = "${column.comment}")
    private Double age;

    @ApiModelProperty(value = "${column.comment}")
    private String idMarriage;

    @ApiModelProperty(value = "${column.comment}")
    private String marriage;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private String idPatientclass;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregister;

    @ApiModelProperty(value = "${column.comment}")
    private String shortCode;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String pictures;

    @ApiModelProperty(value = "${column.comment}")
    private String upFzxId;

    @ApiModelProperty(value = "${column.comment}")
    private String downFzxId;

    @ApiModelProperty(value = "${column.comment}")
    private String upName;

    @ApiModelProperty(value = "${column.comment}")
    private Date downTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date backTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date finishTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private String downName;

    @ApiModelProperty(value = "${column.comment}")
    private String backName;

    @ApiModelProperty(value = "${column.comment}")
    private String finishName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idExamtype;
}

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
 * (Basconclusion)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:22
 */
@Data
@TableName("BASCONCLUSION")
@ApiModel(value = "Basconclusion", description = "$tableInfo.comment实体类")
public class Basconclusion extends Model<Basconclusion> implements Serializable {
    private static final long serialVersionUID = -83546705772163228L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String sysmanmark;

    @ApiModelProperty(value = "${column.comment}")
    private String keyconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionNameeng;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCode;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCode2;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCode3;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionCodehm;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusioncodex;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiontype;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiontype2;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiontype3;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusiongroup;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionlink;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionmedinsurance;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusioncadre;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionoccup;

    @ApiModelProperty(value = "${column.comment}")
    private String idSection;

    @ApiModelProperty(value = "${column.comment}")
    private String depiction;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestion;

    @ApiModelProperty(value = "${column.comment}")
    private String advice;

    @ApiModelProperty(value = "${column.comment}")
    private String dietguide;

    @ApiModelProperty(value = "${column.comment}")
    private String sportguide;

    @ApiModelProperty(value = "${column.comment}")
    private String knowledge;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestiongroup;

    @ApiModelProperty(value = "${column.comment}")
    private String idIcd10;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodeb;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodec;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcoded;

    @ApiModelProperty(value = "${column.comment}")
    private String inputcodee;

    @ApiModelProperty(value = "${column.comment}")
    private String disporder;

    @ApiModelProperty(value = "${column.comment}")
    private String idSpecialconclusionlist;

    @ApiModelProperty(value = "${column.comment}")
    private String fRealdisease;

    @ApiModelProperty(value = "${column.comment}")
    private String fMaledisease;

    @ApiModelProperty(value = "${column.comment}")
    private String fFemaledisease;

    @ApiModelProperty(value = "${column.comment}")
    private String fHideongroupreport;

    @ApiModelProperty(value = "${column.comment}")
    private String disporderOngroupreport;

    @ApiModelProperty(value = "${column.comment}")
    private String conclusionfindermatch;

    @ApiModelProperty(value = "CONCLUSIONMATCHEXCLUDESELF")
    private String conclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String fNormalconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private String severedegree;

    @ApiModelProperty(value = "${column.comment}")
    private String fOccudiseaseobject;

    @ApiModelProperty(value = "${column.comment}")
    private String fOccudiseasecontraindication;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestionoccudisease;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private String status;

    @ApiModelProperty(value = "${column.comment}")
    private String divisionId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isLong;

    @ApiModelProperty(value = "${column.comment}")
    private String depName;

    @ApiModelProperty(value = "0:未删除；1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "${column.comment}")
    private String creater;

    @ApiModelProperty(value = "${column.comment}")
    private String auditer;

    @ApiModelProperty(value = "${column.comment}")
    private Date auditTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer auditStatus;

    @ApiModelProperty(value = "${column.comment}")
    private Integer scbz;

    @ApiModelProperty(value = "是否常用1 常用 0不常用")
    private Integer isInCommonUse;
}

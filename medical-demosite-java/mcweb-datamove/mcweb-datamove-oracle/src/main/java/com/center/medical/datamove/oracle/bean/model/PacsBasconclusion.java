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
 * (PacsBasconclusion)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:38
 */
@Data
@TableName("PACS_BASCONCLUSION")
@ApiModel(value = "PacsBasconclusion", description = "$tableInfo.comment实体类")
public class PacsBasconclusion extends Model<PacsBasconclusion> implements Serializable {
    private static final long serialVersionUID = -92586114852796244L;

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

    @ApiModelProperty(value = "${column.comment}")
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
    private Double status;

    @ApiModelProperty(value = "${column.comment}")
    private String divisionId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isLong;

    @ApiModelProperty(value = "${column.comment}")
    private String depName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPublic;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxIds;
}

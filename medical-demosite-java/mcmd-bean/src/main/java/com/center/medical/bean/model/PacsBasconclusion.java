package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * pacs总检结论词(PacsBasconclusion)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_basconclusion")
@ApiModel(value = "PacsBasconclusion", description = "pacs总检结论词实体类")
public class PacsBasconclusion extends Model<PacsBasconclusion> implements Serializable {
    private static final long serialVersionUID = -71891396911589618L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "维护标记")
    private String sysmanmark;

    @ApiModelProperty(value = "健值")
    private String keyconclusion;

    @ApiModelProperty(value = "结论名称")
    private String name;

    @ApiModelProperty(value = "conclusion_nameeng")
    private String conclusionNameeng;

    @ApiModelProperty(value = "conclusion_code")
    private String conclusionCode;

    @ApiModelProperty(value = "conclusion_code2")
    private String conclusionCode2;

    @ApiModelProperty(value = "conclusion_code3")
    private String conclusionCode3;

    @ApiModelProperty(value = "conclusion_codehm")
    private String conclusionCodehm;

    @ApiModelProperty(value = "conclusioncodex")
    private String conclusioncodex;

    @ApiModelProperty(value = "id_conclusiontype")
    private String idConclusiontype;

    @ApiModelProperty(value = "id_conclusiontype2")
    private String idConclusiontype2;

    @ApiModelProperty(value = "id_conclusiontype3")
    private String idConclusiontype3;

    @ApiModelProperty(value = "id_conclusiongroup")
    private String idConclusiongroup;

    @ApiModelProperty(value = "id_conclusionlink")
    private String idConclusionlink;

    @ApiModelProperty(value = "id_conclusionmedinsurance")
    private String idConclusionmedinsurance;

    @ApiModelProperty(value = "id_conclusioncadre")
    private String idConclusioncadre;

    @ApiModelProperty(value = "id_conclusionoccup")
    private String idConclusionoccup;

    @ApiModelProperty(value = "id_section")
    private String idSection;

    @ApiModelProperty(value = "疾病解释")
    private String depiction;

    @ApiModelProperty(value = "总检建议")
    private String suggestion;

    @ApiModelProperty(value = "健康建议")
    private String advice;

    @ApiModelProperty(value = "饮食指导")
    private String dietguide;

    @ApiModelProperty(value = "运动指导")
    private String sportguide;

    @ApiModelProperty(value = "健康知识")
    private String knowledge;

    @ApiModelProperty(value = "健康知识")
    private String suggestiongroup;

    @ApiModelProperty(value = "id_icd10")
    private String idIcd10;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "inputcodeb")
    private String inputcodeb;

    @ApiModelProperty(value = "inputcodec")
    private String inputcodec;

    @ApiModelProperty(value = "inputcoded")
    private String inputcoded;

    @ApiModelProperty(value = "inputcodee")
    private String inputcodee;

    @ApiModelProperty(value = "disporder")
    private String disporder;

    @ApiModelProperty(value = "id_specialconclusionlist")
    private String idSpecialconclusionlist;

    @ApiModelProperty(value = "f_realdisease")
    private String fRealdisease;

    @ApiModelProperty(value = "f_maledisease")
    private String fMaledisease;

    @ApiModelProperty(value = "f_femaledisease")
    private String fFemaledisease;

    @ApiModelProperty(value = "f_hideongroupreport")
    private String fHideongroupreport;

    @ApiModelProperty(value = "disporder_ongroupreport")
    private String disporderOngroupreport;

    @ApiModelProperty(value = "conclusionfindermatch")
    private String conclusionfindermatch;

    @ApiModelProperty(value = "conclusion")
    private String conclusion;

    @ApiModelProperty(value = "f_normalconclusion")
    private String fNormalconclusion;

    @ApiModelProperty(value = "severedegree")
    private String severedegree;

    @ApiModelProperty(value = "f_occudiseaseobject")
    private String fOccudiseaseobject;

    @ApiModelProperty(value = "f_occudiseasecontraindication")
    private String fOccudiseasecontraindication;

    @ApiModelProperty(value = "suggestionoccudisease")
    private String suggestionoccudisease;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "是否进总检")
    private Integer isLong;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的：0或null.否 1.是")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;
}

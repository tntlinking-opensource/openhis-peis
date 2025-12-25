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
 * (PacsBasexamltem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:41
 */
@Data
@TableName("PACS_BASEXAMLTEM")
@ApiModel(value = "PacsBasexamltem", description = "$tableInfo.comment实体类")
public class PacsBasexamltem extends Model<PacsBasexamltem> implements Serializable {
    private static final long serialVersionUID = 743734815997300707L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String sysmanmark;

    @ApiModelProperty(value = "${column.comment}")
    private String keyexamitem;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemName;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemNameabr;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemNameprn;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemNameeng;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemNameengabr;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemCode;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemCode2;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemCode3;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemCodehm;

    @ApiModelProperty(value = "${column.comment}")
    private String examitemcodex;

    @ApiModelProperty(value = "${column.comment}")
    private String lbdmExamitemtype;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamitemtype;

    @ApiModelProperty(value = "${column.comment}")
    private String idTreegroup;

    @ApiModelProperty(value = "${column.comment}")
    private String idTreegroupsub;

    @ApiModelProperty(value = "${column.comment}")
    private String valuetype;

    @ApiModelProperty(value = "${column.comment}")
    private String valueunit;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuememolines;

    @ApiModelProperty(value = "${column.comment}")
    private Double fAutocalc;

    @ApiModelProperty(value = "${column.comment}")
    private Double fPrint;

    @ApiModelProperty(value = "${column.comment}")
    private Double fMultival;

    @ApiModelProperty(value = "${column.comment}")
    private Integer commongroup;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuecheckcols;

    @ApiModelProperty(value = "${column.comment}")
    private Double fEntryonly;

    @ApiModelProperty(value = "${column.comment}")
    private Double fNoentry;

    @ApiModelProperty(value = "${column.comment}")
    private Double fEntrytoconclusion;

    @ApiModelProperty(value = "${column.comment}")
    private Double fUsecheck;

    @ApiModelProperty(value = "${column.comment}")
    private Double fSameline;

    @ApiModelProperty(value = "${column.comment}")
    private Double fCanDup;

    @ApiModelProperty(value = "${column.comment}")
    private Double fLabByhand;

    @ApiModelProperty(value = "${column.comment}")
    private Double fLabNowait;

    @ApiModelProperty(value = "${column.comment}")
    private Double conclusionlevel;

    @ApiModelProperty(value = "${column.comment}")
    private Double fSummarytitle;

    @ApiModelProperty(value = "${column.comment}")
    private String phraseforhilo;

    @ApiModelProperty(value = "${column.comment}")
    private Double increments;

    @ApiModelProperty(value = "${column.comment}")
    private Double numprecision;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuevalidmin;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuevalidmax;

    @ApiModelProperty(value = "${column.comment}")
    private String itemflagcaselessthan;

    @ApiModelProperty(value = "${column.comment}")
    private String itemflagcasegreaterthan;

    @ApiModelProperty(value = "${column.comment}")
    private Double fBothnumtext;

    @ApiModelProperty(value = "${column.comment}")
    private Double fNumonly;

    @ApiModelProperty(value = "${column.comment}")
    private Double fUseappmaxmin;

    @ApiModelProperty(value = "${column.comment}")
    private Double fIncludemin;

    @ApiModelProperty(value = "${column.comment}")
    private Double fIncludemax;

    @ApiModelProperty(value = "${column.comment}")
    private Double fMale;

    @ApiModelProperty(value = "${column.comment}")
    private String valuemaledef;

    @ApiModelProperty(value = "${column.comment}")
    private String valuemalemax;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuemalenormmid;

    @ApiModelProperty(value = "${column.comment}")
    private String valuemalemin;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuemaleweakup;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuemaleweakdown;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuemalesevereup;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuemaleseveredown;

    @ApiModelProperty(value = "${column.comment}")
    private Double fFemale;

    @ApiModelProperty(value = "${column.comment}")
    private String valuefemaledef;

    @ApiModelProperty(value = "${column.comment}")
    private String valuefemalemax;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuefemalenormmid;

    @ApiModelProperty(value = "${column.comment}")
    private String valuefemalemin;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuefemaleweakup;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuefemaleweakdown;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuefemalesevereup;

    @ApiModelProperty(value = "${column.comment}")
    private Double valuefemaleseveredown;

    @ApiModelProperty(value = "${column.comment}")
    private String gxdm;

    @ApiModelProperty(value = "${column.comment}")
    private String expression;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionhi;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionlo;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionpo;

    @ApiModelProperty(value = "${column.comment}")
    private String idConclusionne;

    @ApiModelProperty(value = "${column.comment}")
    private Double idConclusionweaknormhi;

    @ApiModelProperty(value = "${column.comment}")
    private Double idConclusionweaknormlo;

    @ApiModelProperty(value = "${column.comment}")
    private Double idConclusionweakposihi;

    @ApiModelProperty(value = "${column.comment}")
    private Double idConclusionweakposilo;

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
    private Double fPatientbaseinfo;

    @ApiModelProperty(value = "${column.comment}")
    private Double patientbaseinfodisporder;

    @ApiModelProperty(value = "${column.comment}")
    private String note;

    @ApiModelProperty(value = "${column.comment}")
    private String disporder;

    @ApiModelProperty(value = "${column.comment}")
    private Double fExamitemexportflag01;

    @ApiModelProperty(value = "${column.comment}")
    private Double fExamitemexportflag02;

    @ApiModelProperty(value = "${column.comment}")
    private Double fNodepartsummary;

    @ApiModelProperty(value = "${column.comment}")
    private Double fComparereport;

    @ApiModelProperty(value = "${column.comment}")
    private Double fNoreportoutput;

    @ApiModelProperty(value = "${column.comment}")
    private Double fAlwayspositive;

    @ApiModelProperty(value = "${column.comment}")
    private Double fTitleonreport;

    @ApiModelProperty(value = "${column.comment}")
    private String itemrefrange;

    @ApiModelProperty(value = "${column.comment}")
    private String keysummarygroup;

    @ApiModelProperty(value = "${column.comment}")
    private Double fUseapprangeoccudisease;

    @ApiModelProperty(value = "${column.comment}")
    private Integer expressionoccudisease;

    @ApiModelProperty(value = "${column.comment}")
    private String divisionId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer type;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String interfaceCode;

    @ApiModelProperty(value = "${column.comment}")
    private String valuedangerousmax;

    @ApiModelProperty(value = "${column.comment}")
    private String valuedangerousmin;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDesc;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isName;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPublic;

    @ApiModelProperty(value = "${column.comment}")
    private String fzxIds;
}

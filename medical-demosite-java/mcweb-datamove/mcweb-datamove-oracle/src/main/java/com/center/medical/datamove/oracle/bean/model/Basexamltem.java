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
 * JC检查项目表(Basexamltem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:42
 */
@Data
@TableName("BASEXAMLTEM")
@ApiModel(value = "Basexamltem", description = "JC检查项目表实体类")
public class Basexamltem extends Model<Basexamltem> implements Serializable {
    private static final long serialVersionUID = 738276526043325857L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "系统维护标志")
    private String sysmanmark;

    @ApiModelProperty(value = "KEYEXAMITEM")
    private String keyexamitem;

    @ApiModelProperty(value = "检查项目名称")
    private String examitemName;

    @ApiModelProperty(value = "检查项目名称缩写")
    private String examitemNameabr;

    @ApiModelProperty(value = "检查项目打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "检查项目英文名称")
    private String examitemNameeng;

    @ApiModelProperty(value = "检查项目英文名称缩写")
    private String examitemNameengabr;

    @ApiModelProperty(value = "检查项目代码")
    private String examitemCode;

    @ApiModelProperty(value = "EXAMITEM_CODE2")
    private String examitemCode2;

    @ApiModelProperty(value = "EXAMITEM_CODE3")
    private String examitemCode3;

    @ApiModelProperty(value = "EXAMITEM_CODEHM")
    private String examitemCodehm;

    @ApiModelProperty(value = "导出代码")
    private String examitemcodex;

    @ApiModelProperty(value = "_LBDM_EXAMITEMTYPE")
    private String lbdmExamitemtype;

    @ApiModelProperty(value = "项目类型ID")
    private String idExamitemtype;

    @ApiModelProperty(value = "ID_TREEGROUP")
    private String idTreegroup;

    @ApiModelProperty(value = "ID_TREEGROUPSUB")
    private String idTreegroupsub;

    @ApiModelProperty(value = "值类型")
    private String valuetype;

    @ApiModelProperty(value = "值单位")
    private String valueunit;

    @ApiModelProperty(value = "多行文本行数")
    private String valuememolines;

    @ApiModelProperty(value = "自动计算")
    private String fAutocalc;

    @ApiModelProperty(value = "是否打印")
    private String fPrint;

    @ApiModelProperty(value = "是否多值")
    private String fMultival;

    @ApiModelProperty(value = "通用组(不再使用)")
    private Integer commongroup;

    @ApiModelProperty(value = "多值列数")
    private String valuecheckcols;

    @ApiModelProperty(value = "仅自由输入")
    private String fEntryonly;

    @ApiModelProperty(value = "无自由输入")
    private String fNoentry;

    @ApiModelProperty(value = "输入至小结")
    private String fEntrytoconclusion;

    @ApiModelProperty(value = "使用勾选")
    private String fUsecheck;

    @ApiModelProperty(value = "同行显示")
    private String fSameline;

    @ApiModelProperty(value = "可重复(不再使用)")
    private String fCanDup;

    @ApiModelProperty(value = "检验手工项目")
    private String fLabByhand;

    @ApiModelProperty(value = "检验不必等待")
    private String fLabNowait;

    @ApiModelProperty(value = "小结级别")
    private String conclusionlevel;

    @ApiModelProperty(value = "小结标题")
    private String fSummarytitle;

    @ApiModelProperty(value = "高低用词(不再使用)")
    private String phraseforhilo;

    @ApiModelProperty(value = "数值型增量")
    private String increments;

    @ApiModelProperty(value = "精度")
    private String numprecision;

    @ApiModelProperty(value = "VALUEVALIDMIN")
    private String valuevalidmin;

    @ApiModelProperty(value = "VALUEVALIDMAX")
    private String valuevalidmax;

    @ApiModelProperty(value = "ITEMFLAGCASELESSTHAN")
    private String itemflagcaselessthan;

    @ApiModelProperty(value = "ITEMFLAGCASEGREATERTHAN")
    private String itemflagcasegreaterthan;

    @ApiModelProperty(value = "数字文字同时显示")
    private String fBothnumtext;

    @ApiModelProperty(value = "F_NUMONLY")
    private String fNumonly;

    @ApiModelProperty(value = "应用范围")
    private String fUseappmaxmin;

    @ApiModelProperty(value = "含低值")
    private String fIncludemin;

    @ApiModelProperty(value = "含高值")
    private String fIncludemax;

    @ApiModelProperty(value = "用于男性")
    private String fMale;

    @ApiModelProperty(value = "男性缺省值")
    private String valuemaledef;

    @ApiModelProperty(value = "男性上限")
    private String valuemalemax;

    @ApiModelProperty(value = "VALUEMALENORMMID")
    private String valuemalenormmid;

    @ApiModelProperty(value = "男性下限")
    private String valuemalemin;

    @ApiModelProperty(value = "VALUEMALEWEAKUP")
    private String valuemaleweakup;

    @ApiModelProperty(value = "VALUEMALEWEAKDOWN")
    private String valuemaleweakdown;

    @ApiModelProperty(value = "男性生命值上限")
    private String valuemalesevereup;

    @ApiModelProperty(value = "男性生命值下限")
    private String valuemaleseveredown;

    @ApiModelProperty(value = "用于女性")
    private String fFemale;

    @ApiModelProperty(value = "女性缺省值")
    private String valuefemaledef;

    @ApiModelProperty(value = "女性上限")
    private String valuefemalemax;

    @ApiModelProperty(value = "VALUEFEMALENORMMID")
    private String valuefemalenormmid;

    @ApiModelProperty(value = "女性下限")
    private String valuefemalemin;

    @ApiModelProperty(value = "VALUEFEMALEWEAKUP")
    private String valuefemaleweakup;

    @ApiModelProperty(value = "VALUEFEMALEWEAKDOWN")
    private String valuefemaleweakdown;

    @ApiModelProperty(value = "女性生命值上限")
    private String valuefemalesevereup;

    @ApiModelProperty(value = "女性生命值下限")
    private String valuefemaleseveredown;

    @ApiModelProperty(value = "_GXDM")
    private String gxdm;

    @ApiModelProperty(value = "表达式")
    private String expression;

    @ApiModelProperty(value = "结论词(高)")
    private String idConclusionhi;

    @ApiModelProperty(value = "结论词(低)")
    private String idConclusionlo;

    @ApiModelProperty(value = "结论词(阳)")
    private String idConclusionpo;

    @ApiModelProperty(value = "结论词(阴)")
    private String idConclusionne;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKNORMHI")
    private String idConclusionweaknormhi;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKNORMLO")
    private String idConclusionweaknormlo;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKPOSIHI")
    private String idConclusionweakposihi;

    @ApiModelProperty(value = "ID_CONCLUSIONWEAKPOSILO")
    private String idConclusionweakposilo;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "INPUTCODEB")
    private String inputcodeb;

    @ApiModelProperty(value = "INPUTCODEC")
    private String inputcodec;

    @ApiModelProperty(value = "INPUTCODED")
    private String inputcoded;

    @ApiModelProperty(value = "INPUTCODEE")
    private String inputcodee;

    @ApiModelProperty(value = "(不使用)")
    private String fPatientbaseinfo;

    @ApiModelProperty(value = "(不使用 )")
    private String patientbaseinfodisporder;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "行序")
    private String disporder;

    @ApiModelProperty(value = "F_EXAMITEMEXPORTFLAG01")
    private String fExamitemexportflag01;

    @ApiModelProperty(value = "F_EXAMITEMEXPORTFLAG02")
    private String fExamitemexportflag02;

    @ApiModelProperty(value = "F_NODEPARTSUMMARY")
    private String fNodepartsummary;

    @ApiModelProperty(value = "F_COMPAREREPORT")
    private String fComparereport;

    @ApiModelProperty(value = "F_NOREPORTOUTPUT")
    private String fNoreportoutput;

    @ApiModelProperty(value = "F_ALWAYSPOSITIVE")
    private String fAlwayspositive;

    @ApiModelProperty(value = "F_TITLEONREPORT")
    private String fTitleonreport;

    @ApiModelProperty(value = "ITEMREFRANGE")
    private String itemrefrange;

    @ApiModelProperty(value = "KEYSUMMARYGROUP")
    private String keysummarygroup;

    @ApiModelProperty(value = "F_USEAPPRANGEOCCUDISEASE")
    private String fUseapprangeoccudisease;

    @ApiModelProperty(value = "EXPRESSIONOCCUDISEASE")
    private Integer expressionoccudisease;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "0:健康检查类型 1:职业检查类型 2:健康+职业(职业)")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "接口代码")
    private String interfaceCode;

    @ApiModelProperty(value = "危急值上限")
    private String valuedangerousmax;

    @ApiModelProperty(value = "男性生命值下限")
    private String valuedangerousmin;

    @ApiModelProperty(value = "描述进小结")
    private Integer isDesc;

    @ApiModelProperty(value = "名称进小结")
    private Integer isName;

    @ApiModelProperty(value = "0：解锁 1：锁定")
    private Integer status;

    @ApiModelProperty(value = "0:未删除 1：删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "${column.comment}")
    private String devicetypePositionCheckitem;

    @ApiModelProperty(value = "${column.comment}")
    private Integer addUnit;
}
